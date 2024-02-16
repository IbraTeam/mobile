package com.ibra.keytrackerapp.keytrack.presentation

import androidx.lifecycle.ViewModel
import com.ibra.keytrackerapp.common.enums.KeyCardType
import com.ibra.keytrackerapp.common.keytracker.data.KeyCardData
import com.ibra.keytrackerapp.keytrack.domain.models.KeyCardDto
import com.ibra.keytrackerapp.keytrack.domain.models.PersonDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject



@HiltViewModel
class KeyTrackerViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(KeyTrackerUiState())
    val uiState: StateFlow<KeyTrackerUiState> = _uiState.asStateFlow()
    fun onExitButtonPressed() {
        TODO("Not yet implemented")
    }

    fun onFirstButtonPressed(keyCard: KeyCardData) {

    }

    fun onSecondButtonPressed(keyCard: KeyCardData) {

    }



    init {
        initViewModel()
    }

    private fun initViewModel() {
        // fill key card dto list with fake data
        val keyCardDtoList = mutableListOf<KeyCardDto>()
        keyCardDtoList.add(
            KeyCardDto(
                id = "1",
                type = KeyCardType.ON_HAND,
                auditory = "A-101",
                date = "12.12.2021",
                time = "12:00"
            )
        )
        keyCardDtoList.add(
            KeyCardDto(
                id = "2",
                type = KeyCardType.HAVE_OFFER,
                auditory = "A-102",
                date = "12.12.2021",
                time = "12:00",
                person = "Ivanov Ivan"
            )
        )
        keyCardDtoList.add(
            KeyCardDto(
                id = "3",
                type = KeyCardType.WAIT,
                auditory = "A-103",
                date = "12.12.2021",
                time = "12:00"
            )
        )

        keyCardDtoList.add(
            KeyCardDto(
                id = "3",
                type = KeyCardType.SUGGEST_OFFER,
                auditory = "A-103",
                date = "12.12.2021",
                time = "12:00",
                person = "Ivanov Ivan"
            )
        )
        keyCardDtoList.add(
            KeyCardDto(
                id = "2",
                type = KeyCardType.HAVE_OFFER,
                auditory = "A-102",
                date = "12.12.2021",
                time = "12:00",
                person = "Ivanov Ivan"
            )
        )
        keyCardDtoList.add(
            KeyCardDto(
                id = "3",
                type = KeyCardType.WAIT,
                auditory = "A-103",
                date = "12.12.2021",
                time = "12:00"
            )
        )

        keyCardDtoList.add(
            KeyCardDto(
                id = "3",
                type = KeyCardType.SUGGEST_OFFER,
                auditory = "A-103",
                date = "12.12.2021",
                time = "12:00",
                person = "Ivanov Ivan"
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

        _uiState.value = KeyTrackerUiState(keyCardDtoList = keyCardDtoList, personList = personList)

    }

    fun onSheetDismissed() {
        _uiState.value = _uiState.value.copy(isSheetVisible = false)
    }

    fun onSheetExpanded() {
        _uiState.value = _uiState.value.copy(isSheetVisible = true)
    }

    fun onPersonSelected(id: String) {

    }

    fun onPersonNameChanged(s: String) {

    }


}


data class KeyTrackerUiState(
    val keyCardDtoList: List<KeyCardDto> = emptyList(),
    val isExitButtonPressed: Boolean = false,
    val isSheetVisible: Boolean = false,
    val personList: List<PersonDto> = emptyList(),
    val personName: String = ""
) {
}