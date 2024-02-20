package com.ibra.keytrackerapp.key_requests.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibra.keytrackerapp.common.auth.domain.usecase.LogoutUserUseCase
import com.ibra.keytrackerapp.common.profile.domain.model.Profile
import com.ibra.keytrackerapp.common.profile.domain.usecase.ProfileUseCase
import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import com.ibra.keytrackerapp.key_requests.domain.model.KeyRequestDto
import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests
import com.ibra.keytrackerapp.key_requests.domain.use_case.KeyRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val keyRequestUseCase: KeyRequestUseCase,
    private val profileUseCase: ProfileUseCase,
    private val tokenUseCase: TokenUseCase,
    private val logoutUseCase: LogoutUserUseCase
) : ViewModel()
{
    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    init {
        selectDate(LocalDate.now())
        getProfile()
    }

    // Полученине профиля пользователя
    fun getProfile() {
        viewModelScope.launch(Dispatchers.Default) {
            val profile = profileUseCase.getProfile(tokenUseCase.getTokenFromLocalStorage()).body()
            _uiState.value = _uiState.value.copy(profile = profile)

            if (_uiState.value.selectedWeek.isNotEmpty())
                _uiState.value = _uiState.value.copy(userRequests = keyRequestUseCase.getUserRequests(_uiState.value.selectedWeek[0]))
        }
    }

    // Выход из аккаунта
    fun logout() {
        viewModelScope.launch(Dispatchers.Default) {
            logoutUseCase.execute("Bearer ${tokenUseCase.getTokenFromLocalStorage()}")
        }
    }

    // Удаление заявки
    fun deleteRequest(requestId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            keyRequestUseCase.deleteRequest(requestId)
            _uiState.value = _uiState.value.copy(userRequests = keyRequestUseCase.getUserRequests(_uiState.value.selectedWeek[0]))
        }
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

        if (_uiState.value.userRequests != null && _uiState.value.userRequests!!.requests.isNotEmpty())
            _uiState.value = _uiState.value.copy(
                dayRequests = keyRequestUseCase.getDayRequests(_uiState.value.selectedDate, _uiState.value.userRequests!!.requests)
            )

        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = _uiState.value.copy(userRequests = keyRequestUseCase.getUserRequests(_uiState.value.selectedWeek[0]))
        }
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
    val dayRequests : MutableList<KeyRequestDto> = mutableListOf(),
    val userRequests: UserRequests? = null,
    val profile : Profile? = null
)