package com.ibra.keytrackerapp.key_requests.presentation

import androidx.lifecycle.ViewModel
import com.ibra.keytrackerapp.key_requests.domain.model.KeyRequestDto
import com.ibra.keytrackerapp.key_requests.domain.use_case.KeyRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val keyRequestUseCase: KeyRequestUseCase
) : ViewModel()
{
    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    init {
        selectWeek()
        generateRequests()
        _uiState.value = _uiState.value.copy(
            dayRequests = keyRequestUseCase.getDayRequests(_uiState.value.selectedDate, _uiState.value.keyRequests))
    }

    // Генерация списка заявок пользователя
    private fun generateRequests() {
        _uiState.value = _uiState.value.copy(keyRequests = keyRequestUseCase.generateRequests())
    }

    // Перевод номера дня недели в его название
    fun getDayOfWeekName(dayOfWeek: Int) : String {
        return keyRequestUseCase.getDayOfWeekName(dayOfWeek)
    }

    // Получение времени начала пары
    fun getPairStartTime(pairNum : Int) : String {
        return keyRequestUseCase.getPairStartTime(pairNum)
    }

    // Получение времени конца пары
    fun getPairEndTime(pairNum : Int) : String {
        return keyRequestUseCase.getPairEndTime(pairNum)
    }

    // Получение названия пары
    fun getPairName(keyRequestDto: KeyRequestDto) : String {
        return keyRequestUseCase.getPairName(keyRequestDto)
    }

    // Выбор даты
    fun selectDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
        selectWeek()
        _uiState.value = _uiState.value.copy(
            dayRequests = keyRequestUseCase.getDayRequests(_uiState.value.selectedDate, _uiState.value.keyRequests))
    }

    // Получение названия статуса заявки
    fun getRequestStatusName(keyRequestDto: KeyRequestDto) : String {
        return keyRequestUseCase.getRequestStatusName(keyRequestDto)
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
    val selectedWeek : MutableList<LocalDate> = mutableListOf(),
    val keyRequests : MutableList<KeyRequestDto> = mutableListOf(),
    val dayRequests : MutableList<KeyRequestDto> = mutableListOf()
)