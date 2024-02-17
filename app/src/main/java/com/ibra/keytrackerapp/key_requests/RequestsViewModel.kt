package com.ibra.keytrackerapp.key_requests

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
        val currentDate = LocalDate.now()
        val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
        val week : MutableList<LocalDate> = mutableListOf()

        for (i in 0..6)
            week.add(startOfWeek.plusDays(i.toLong()))

        _uiState.value = RequestUiState(selectedWeek = week)
    }

    // Перевод номера дня недели в его название
    fun GetDayOfWeekName(dayOfWeek: Int) : String {
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
}

// Данные о состоянии экрана
data class RequestUiState(
    val selectedDate : LocalDate = LocalDate.now(),
    val selectedWeek : MutableList<LocalDate> = mutableListOf()
)