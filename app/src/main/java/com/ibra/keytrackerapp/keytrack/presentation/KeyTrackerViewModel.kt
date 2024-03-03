package com.ibra.keytrackerapp.keytrack.presentation

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.keytrackerapp.common.auth.domain.usecase.LogoutUserUseCase
import com.ibra.keytrackerapp.common.enums.TransferStatus
import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.model.PagePaginationDto
import com.ibra.keytrackerapp.common.keytrack.domain.usecase.KeyTrackUseCases
import com.ibra.keytrackerapp.common.profile.domain.usecase.ProfileUseCase
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KeyTrackerViewModel @Inject constructor(
    private val keyTrackUseCases: KeyTrackUseCases,
    private val tokenUseCase: TokenUseCase,
    private val profileUseCase: ProfileUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(KeyTrackerUiState())
    val uiState: StateFlow<KeyTrackerUiState> = _uiState.asStateFlow()

    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val token = tokenUseCase.getTokenFromLocalStorage()
                val isTokenExpired = tokenUseCase.isTokenExpired(token)

                if (!isTokenExpired) {
                    handleValidToken(token)
                } else {
                    handleExpiredToken(token)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(isLogout = true)
                }
            }
        }
    }

    fun onExitButtonPressed() {
        viewModelScope.launch(Dispatchers.Default) {
            val token = tokenUseCase.getTokenFromLocalStorage()
            logoutUserUseCase.execute(token)
            tokenUseCase.deleteTokenFromLocalStorage()
            profileUseCase.deleteProfileFromLocalStorage()

            _uiState.value = _uiState.value.copy(isLogout = true)
        }
    }

    fun onFirstButtonPressed(keyDto: KeyDto) {
        viewModelScope.launch(Dispatchers.Default) {
            val token = getTokenIfNotExpired()

            when (keyDto.transferStatus) {
                TransferStatus.OFFERING_TO_YOU -> keyTrackUseCases.rejectKey(
                    token, keyDto.keyId
                )

                TransferStatus.IN_DEAN -> keyTrackUseCases.getKey(token, keyDto.keyId)
                else -> {}
            }
            _uiState.update { currentState ->
                currentState.copy(
                    transferKeyId = if (keyDto.transferStatus == TransferStatus.ON_HANDS) keyDto.keyId else ""
                )
            }
            updateKeysList(token)
        }

    }

    private suspend fun getTokenIfNotExpired(): String {
        val token = tokenUseCase.getTokenFromLocalStorage()
        if (tokenUseCase.isTokenExpired(token)) {
            handleExpiredToken(token)
        }
        return token
    }

    fun onSecondButtonPressed(keyDto: KeyDto) {
        viewModelScope.launch(Dispatchers.Default) {
            val token = getTokenIfNotExpired()
            when (keyDto.transferStatus) {
                TransferStatus.ON_HANDS -> keyTrackUseCases.returnKey(token, keyDto.keyId)
                TransferStatus.OFFERING_TO_YOU -> keyTrackUseCases.acceptKey(
                    token, keyDto.keyId
                )
                TransferStatus.TRANSFERRING -> keyTrackUseCases.cancelKey(
                    token, keyDto.keyId
                )
                else -> {}
            }
            updateKeysList(token)
        }
    }

    private suspend fun updateKeysList(token: String) {
        val newKeys = getKeys(token)
        _uiState.update { currentState ->
            currentState.copy(keys = newKeys)
        }
    }


    private suspend fun handleValidToken(token: String) {
        val profileResponse = profileUseCase.getProfile(token)
        val profile = profileResponse.body()
        updateKeysList(token)

        val peopleResponse = keyTrackUseCases.getPeople(token, 0, null)
        val personList = peopleResponse.body()?.users ?: emptyList()
        val pagination = peopleResponse.body()?.page ?: PagePaginationDto()
        _uiState.update { currentState ->
            currentState.copy(
                isLogout = false,
                personName = profile?.name ?: "",
                people = personList,
                pagination = pagination
            )
        }
    }

    fun afterLogout() {
        _uiState.update { currentState ->
            currentState.copy(isLogout = false)
        }
    }

    private suspend fun handleExpiredToken(token: String) {
        profileUseCase.deleteProfileFromLocalStorage()
        tokenUseCase.deleteTokenFromLocalStorage()
        logoutUserUseCase.execute(token)
        _uiState.update { currentState ->
            currentState.copy(
                isLogout = true
            )
        }
    }

    fun onPersonListScrolledToEnd(lazyListState: LazyListState) {
        viewModelScope.launch(Dispatchers.Default) {
            val lastItemIndex = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val itemCount = lazyListState.layoutInfo.totalItemsCount
            if (lastItemIndex >= itemCount - 3) {
                val token = getTokenIfNotExpired()
                val currentPagination = _uiState.value.pagination
                if (currentPagination.currentPage < currentPagination.totalPages) {
                    val peopleResponse = keyTrackUseCases.getPeople(
                        token, currentPagination.currentPage + 1, _uiState.value.personName
                    )

                    val newPeople = _uiState.value.people + peopleResponse.body()?.users.orEmpty()
                    val pagination = peopleResponse.body()?.page ?: PagePaginationDto()
                    _uiState.update { currentState ->
                        currentState.copy(
                            people = newPeople, pagination = pagination
                        )
                    }
                }
            }
        }
    }

    fun onSheetDismissed() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isSheetVisible = false, personName = "")
            }
        }
    }

    fun onSheetExpanded() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isSheetVisible = true, personName = "")
            }
        }
    }

    fun onPersonSelected(userId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val giveKeyResponse = keyTrackUseCases.giveKey(
                getTokenIfNotExpired(), userId, _uiState.value.transferKeyId
            )
            if (giveKeyResponse.isSuccessful) {
                val keyCardList =
                    _uiState.value.keys.filter { it.keyId != _uiState.value.transferKeyId }
                _uiState.update { currentState ->
                    currentState.copy(isSheetVisible = false, keys = keyCardList)
                }
            }
        }
    }

    private suspend fun getKeys(token: String): List<KeyDto> {
        return try {
            val getKeysResponse = keyTrackUseCases.getKeys(token)
            if (getKeysResponse.isSuccessful && getKeysResponse.body() != null) getKeysResponse.body()!!
            else _uiState.value.keys
        } catch (e: Exception) {
            _uiState.value.keys
        }
    }

    @OptIn(FlowPreview::class)
    fun onFieldChanged(newValue: Any) {
        viewModelScope.launch {
            val valueFlow = flowOf(newValue as String).debounce(300).distinctUntilChanged()
            valueFlow.collect { newPersonName ->
                _uiState.update { currentState ->
                    currentState.copy(
                        personName = newPersonName,
                    )
                }
            }
            val token = getTokenIfNotExpired()
            val newPeople = keyTrackUseCases.getPeople(
                token, 0, _uiState.value.personName
            )

            _uiState.update { currentState ->
                currentState.copy(
                    people = newPeople.body()?.users ?: emptyList(),
                    pagination = newPeople.body()?.page ?: PagePaginationDto()
                )
            }
        }
    }

    fun updateKeys() {
        viewModelScope.launch(Dispatchers.Default) {
            updateKeys()
        }
    }


}


