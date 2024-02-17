package com.ibra.keytrackerapp.key_requests.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    init {
        selectWeek()
    }

    // Перевод номера дня недели в его название
    fun getDayOfWeekName(dayOfWeek: Int) : String {
        return when (dayOfWeek) {
            1 -> "Пн"
            2 -> "Вт"
            3 -> "Ср"
            4 -> "Чт"
            5 -> "Пт"
            6 -> "Сб"
            7 -> "Вс"
            else -> ""
        }
    }

    // Выбор даты
    fun selectDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
        selectWeek()
    }

    // Получение недели, содержащей выбранную дату
    private fun selectWeek() {
        val currentDate = _uiState.value.selectedDate
        val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
        val week : MutableList<LocalDate> = mutableListOf()

        for (i in 0..6)
            week.add(startOfWeek.plusDays(i.toLong()))

        _uiState.value = _uiState.value.copy(selectedWeek = week)
    }
}

// Данные о состоянии экрана
data class RequestUiState(
    val selectedDate : LocalDate = LocalDate.now(),
    val selectedWeek : MutableList<LocalDate> = mutableListOf()
)