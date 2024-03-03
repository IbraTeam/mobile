package com.ibra.keytrackerapp.create_request.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.ibra.keytrackerapp.common.enums.PairNumber
import com.ibra.keytrackerapp.common.navigation.Screen
import com.ibra.keytrackerapp.create_request.domain.model.CreateRequestDto
import com.ibra.keytrackerapp.create_request.domain.model.FreeKey
import com.ibra.keytrackerapp.create_request.domain.use_case.CreateRequestUseCase
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
        getFreeKeys()
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

        getFreeKeys()
    }

    // Ввод кол-ва недель
    fun onWeeksChanged(weeks: String) {
        if (weeks.toIntOrNull() == null && weeks.isNotEmpty() ||
            weeks.toIntOrNull() != null && (weeks.toInt() <= 0 || weeks.toInt() > 10)) {
            _uiState.value = _uiState.value.copy(isError = true)
        }
        else {
            _uiState.value = _uiState.value.copy(isError = false)
            _uiState.value = _uiState.value.copy(weeks = weeks.toIntOrNull() ?: 1)
        }
    }

    fun onSendRequestButtonClick() {
        val requestValues = _uiState.value

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var date = requestValues.selectedDate.format(formatter)
        date = "${date}T06:05:48.750Z"

        val request = CreateRequestDto(
            dateTime = date,
            RepeatCount = requestValues.weeks,
            typeBooking = RequestType.Booking,
            pairNumber = requestValues.selectedPair,
            keyId = requestValues.selectedKey!!.keyId,
            pairName = null
        )

        viewModelScope.launch(Dispatchers.Default) {
            try{
                createRequestUseCase.createRequest(request)
            }
            catch (e : Exception) { }
        }
    }

    // Получение списка свободных аудиторий
    private fun getFreeKeys() {
        val requestValues = _uiState.value
        val date = requestValues.selectedDate.toString()
        val pair = requestValues.selectedPair.ordinal

        viewModelScope.launch {
            try {
                val freeKeys = createRequestUseCase.getFreeKeys(date, pair, 1)
                _uiState.value = _uiState.value.copy(freeKeys = freeKeys)

                updateKeySelection()

            } catch (e: Exception) {
            }
        }
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

        getFreeKeys()
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