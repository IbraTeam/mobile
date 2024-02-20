package com.ibra.keytrackerapp.create_request

import androidx.lifecycle.ViewModel
import com.ibra.keytrackerapp.create_request.domain.CreateRequestUseCase
import com.ibra.keytrackerapp.key_requests.domain.enums.PairNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateRequestViewModel @Inject constructor(
    private val createRequestUseCase: CreateRequestUseCase
) : ViewModel()
{
    private val _uiState = MutableStateFlow(CreateRequestUiState())
    val uiState : StateFlow<CreateRequestUiState> = _uiState.asStateFlow()

    init {
        updateDatesRow()
    }

    // Нажатие на кнопку Отмена
    fun onCancelButtonClick() {

    }

    // Получение названия дня недели
    fun getDayOfWeekName(dayOfWeek: Int): String {
        return createRequestUseCase.getDayOfWeekName(dayOfWeek)
    }

    // Обновление строки с быстрым выбором даты
    private fun updateDatesRow() {
        val selectedDate = _uiState.value.selectedDate
        val datesRow = _uiState.value.datesRow

        if (selectedDate !in datesRow)
            _uiState.value = _uiState.value.copy(datesRow = createRequestUseCase.getDatesRow(selectedDate))
    }

    // Выбор новой даты
    fun selectDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
        updateDatesRow()
    }
}

data class CreateRequestUiState(
    val selectedDate : LocalDate = LocalDate.now(),
    val datesRow : List<LocalDate> = listOf(),
    val selectedPair : PairNumber = PairNumber.First
)