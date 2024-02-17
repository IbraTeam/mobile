package com.ibra.keytrackerapp.keytrack.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.common.enums.TransferStatus
import com.ibra.keytrackerapp.common.keytrack.domain.model.KeyDto
import com.ibra.keytrackerapp.common.keytrack.domain.usecase.KeyTrackUseCases
import com.ibra.keytrackerapp.common.keytrack.domain.model.PersonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class KeyTrackerViewModel @Inject constructor(
    private val keyTrackUseCases: KeyTrackUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(KeyTrackerUiState())
    val uiState: StateFlow<KeyTrackerUiState> = _uiState.asStateFlow()
    fun onExitButtonPressed() {
        TODO("Not yet implemented")
    }

    fun onFirstButtonPressed(keyCardDto: KeyDto) {
        viewModelScope.launch(Dispatchers.Default) {
            val response = when (keyCardDto.transferStatus) {
                TransferStatus.OFFERING_TO_YOU -> keyTrackUseCases.rejectKey("token", keyCardDto.id)
                TransferStatus.IN_DEAN -> keyTrackUseCases.getKey("token", keyCardDto.id)
                TransferStatus.TRANSFERRING -> keyTrackUseCases.cancelKey("token", keyCardDto.id)
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
            val response = when (keyCardDto.transferStatus) {
                TransferStatus.ON_HANDS -> keyTrackUseCases.returnKey("token", keyCardDto.id)
                TransferStatus.OFFERING_TO_YOU -> keyTrackUseCases.acceptKey("token", keyCardDto.id)
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


    init {
        initViewModel()
    }

    private fun initViewModel() {
        val keyCardDtoList = mutableListOf<KeyDto>()
        keyCardDtoList.add(
            KeyDto(
                id = "1",
                transferStatus = TransferStatus.ON_HANDS,
                room = "A-101",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.Fourth
            )
        )
        keyCardDtoList.add(
            KeyDto(
                id = "2",
                transferStatus = TransferStatus.OFFERING_TO_YOU,
                room = "A-102",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.Second,
                userName = "Ivanov Ivan"
            )
        )
        keyCardDtoList.add(
            KeyDto(
                id = "3",
                transferStatus = TransferStatus.IN_DEAN,
                room = "A-103",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.First
            )
        )

        keyCardDtoList.add(
            KeyDto(
                id = "3",
                transferStatus = TransferStatus.TRANSFERRING,
                room = "A-103",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.First,
                userName = "Ivanov Ivan"
            )
        )
        keyCardDtoList.add(
            KeyDto(
                id = "2",
                transferStatus = TransferStatus.OFFERING_TO_YOU,
                room = "A-102",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.First,
                userName = "Ivanov Ivan"
            )
        )
        keyCardDtoList.add(
            KeyDto(
                id = "3",
                transferStatus = TransferStatus.IN_DEAN,
                room = "A-103",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.First
            )
        )

        keyCardDtoList.add(
            KeyDto(
                id = "3",
                transferStatus = TransferStatus.TRANSFERRING,
                room = "A-103",
                dateTime = LocalDateTime.now(),
                pairNumber = PairNumber.First,
                userName = "Ivanov Ivan"
            )
        )

        val personList = mutableListOf<PersonDto>()

        personList.add(PersonDto(id = "1", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "2", name = "Petrov Petr"))
        personList.add(PersonDto(id = "3", name = "Sidorov Sidor"))
        personList.add(PersonDto(id = "4", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "1", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "2", name = "Petrov Petr"))
        personList.add(PersonDto(id = "3", name = "Sidorov Sidor"))
        personList.add(PersonDto(id = "4", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "1", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "2", name = "Petrov Petr"))
        personList.add(PersonDto(id = "3", name = "Sidorov Sidor"))
        personList.add(PersonDto(id = "4", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "1", name = "Ivanov Ivan"))
        personList.add(PersonDto(id = "2", name = "Petrov Petr"))
        personList.add(PersonDto(id = "3", name = "Sidorov Sidor"))
        personList.add(PersonDto(id = "4", name = "Ivanov Ivan"))

        _uiState.value = KeyTrackerUiState(keyDtoList = keyCardDtoList, personList = personList)

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
            val giveKeyResponse = keyTrackUseCases.giveKey("token", userId, keyId)
            if (giveKeyResponse.isSuccessful) {
                val keyCardList = _uiState.value.keyDtoList.filter { it.id != keyId }
                _uiState.update { currentState ->
                    currentState.copy(isSheetVisible = false, keyDtoList = keyCardList)
                }
            }
        }
    }


    private suspend fun getKeyCardList(): List<KeyDto> {
        val getKeysResponse = keyTrackUseCases.getKeys("token")
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


data class KeyTrackerUiState(
    val keyDtoList: List<KeyDto> = emptyList(),
    val isExitButtonPressed: Boolean = false,
    val isSheetVisible: Boolean = false,
    val personList: List<PersonDto> = emptyList(),
    val personName: String = "",
    val transferKeyId: String = ""
)