package com.ibra.keytrackerapp.keytrack.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.keytrackerapp.common.auth.domain.usecase.LogoutUserUseCase
import com.ibra.keytrackerapp.common.enums.TransferStatus
import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.usecase.KeyTrackUseCases
import com.ibra.keytrackerapp.common.profile.domain.usecase.ProfileUseCase
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KeyTrackerViewModel @Inject constructor(
    private val keyTrackUseCases: KeyTrackUseCases,
    private val tokenUseCase: TokenUseCase,
    private val profileUseCase: ProfileUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(KeyTrackerUiState())
    val uiState: StateFlow<KeyTrackerUiState> = _uiState.asStateFlow()


    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch(Dispatchers.Main) {
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
            logoutUserUseCase.execute(tokenUseCase.getTokenFromLocalStorage())
            tokenUseCase.deleteTokenFromLocalStorage()
            profileUseCase.deleteProfileFromLocalStorage()
            _uiState.update { currentState ->
                currentState.copy(isLogout = true)
            }
        }
    }

    fun onFirstButtonPressed(keyCardDto: KeyDto) {
        viewModelScope.launch(Dispatchers.Default) {
            val token = tokenUseCase.getTokenFromLocalStorage()

            val response = when (keyCardDto.transferStatus) {
                TransferStatus.OFFERING_TO_YOU -> keyTrackUseCases.rejectKey(token, keyCardDto.id)
                TransferStatus.IN_DEAN -> keyTrackUseCases.getKey(token, keyCardDto.id)
                TransferStatus.TRANSFERRING -> keyTrackUseCases.cancelKey(token, keyCardDto.id)
                else -> null
            }
            if (response?.isSuccessful == true) {
                val newKeys = getKeyCardList()
                _uiState.update { currentState ->
                    currentState.copy(keyDtoList = newKeys)
                }
            }
        }
    }

    fun onSecondButtonPressed(keyCardDto: KeyDto) {
        viewModelScope.launch(Dispatchers.Default) {
            val token = tokenUseCase.getTokenFromLocalStorage()
            val response = when (keyCardDto.transferStatus) {
                TransferStatus.ON_HANDS -> keyTrackUseCases.returnKey(token, keyCardDto.id)
                TransferStatus.OFFERING_TO_YOU -> keyTrackUseCases.acceptKey(token, keyCardDto.id)
                else -> null
            }
            if (response?.isSuccessful == true) {
                val newKeys = getKeyCardList()
                _uiState.update { currentState ->
                    currentState.copy(keyDtoList = newKeys)
                }

            }
        }
    }




    private suspend fun handleValidToken(token: String) {
        val profileResponse = profileUseCase.getProfile(token)

        val profile = profileResponse.body()
        profile?.let {
            val keyCardList = getKeyCardList()
            _uiState.update { currentState ->
                currentState.copy(
                    keyDtoList = keyCardList,
                    personName = if (profileResponse.isSuccessful) profile.name else ""
                )
            }
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



    fun onSheetDismissed() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isSheetVisible = false, transferKeyId = "")
            }
        }
    }

    fun onSheetExpanded() {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(isSheetVisible = true)
            }
        }
    }

    fun onPersonSelected(keyId: String, userId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val giveKeyResponse = keyTrackUseCases.giveKey(tokenUseCase.getTokenFromLocalStorage(), userId, keyId)
            if (giveKeyResponse.isSuccessful) {
                val keyCardList = _uiState.value.keyDtoList.filter { it.id != keyId }
                _uiState.update { currentState ->
                    currentState.copy(isSheetVisible = false, keyDtoList = keyCardList)
                }
            }
        }
    }


    private suspend fun getKeyCardList(): List<KeyDto> {
        val getKeysResponse = keyTrackUseCases.getKeys(tokenUseCase.getTokenFromLocalStorage())
        return if (getKeysResponse.isSuccessful && getKeysResponse.body() != null)
            getKeysResponse.body()!!.keys
            else _uiState.value.keyDtoList
    }

    fun onPersonNameChanged(newPersonName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            _uiState.update { currentState ->
                currentState.copy(personName = newPersonName)
            }
        }
    }
}


