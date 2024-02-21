package com.ibra.keytrackerapp.create_request.presentation

import androidx.lifecycle.ViewModel
import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.create_request.domain.model.FreeKey
import com.ibra.keytrackerapp.create_request.domain.use_case.CreateRequestUseCase
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

        // Строка для генерации аудиторий, после добавления запросов её можно убирать
        _uiState.value = _uiState.value.copy(freeKeys = createRequestUseCase.generageFreeKeys())
    }

    // Выбор свободной аудитории
    fun onSelectKey(room: FreeKey) {
        _uiState.value = _uiState.value.copy(selectedKey = room)
    }

    // Нажатие на выпадающий список пар
    fun onDropDownMenuClick() {
        _uiState.value = _uiState.value.copy(isPairSelecting = !_uiState.value.isPairSelecting)
    }

    // Выбор пары из выпадающего списка
    fun onPairSelected(pair: PairNumber) {
        _uiState.value = _uiState.value.copy(selectedPair = pair)
        onDropDownMenuClick()


        // Обновление выделения аудиторий надо оставить ниже строк выполнения запроса их получения
        updateKeySelection()
    }

    // Ввод кол-ва недель
    fun onWeeksChanged(weeks: String) {
        if (weeks.toIntOrNull() == null && weeks.isNotEmpty() || weeks.toIntOrNull() != null && weeks.toInt() <= 0) {
            _uiState.value = _uiState.value.copy(isError = true)
        }
        else {
            _uiState.value = _uiState.value.copy(isError = false)
            _uiState.value = _uiState.value.copy(weeks = weeks.toIntOrNull() ?: 1)
        }
    }

    fun onSendRequestButtonClick() {

    }

    // Нажатие на кнопку Отмена
    fun onCancelButtonClick() {

    }

    // Сбрасывает выбор ключа, если он пропадает из списка доступных
    private fun updateKeySelection() {
        val selectedKey = _uiState.value.selectedKey
        val freeKeys = _uiState.value.freeKeys

        if (selectedKey !in freeKeys)
            _uiState.value = _uiState.value.copy(selectedKey = null)
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


        // Обновление выделения аудиторий надо оставить ниже строк выполнения запроса их получения
        updateKeySelection()
    }

    // Получение времени начала пары
    fun getPairStartTime(pairNum : Int) : String {
        return createRequestUseCase.getPairStartTime(pairNum)
    }

    // Получение времени конца пары
    fun getPairEndTime(pairNum : Int) : String {
        return createRequestUseCase.getPairEndTime(pairNum)
    }
}

data class CreateRequestUiState(
    val selectedDate : LocalDate = LocalDate.now(),
    val datesRow : List<LocalDate> = listOf(),
    val selectedPair : PairNumber = PairNumber.First,
    val isPairSelecting : Boolean = false,
    val isError : Boolean = false,
    val weeks : Int = 1,
    val freeKeys : List<FreeKey> = listOf(),
    val selectedKey : FreeKey? = null
)