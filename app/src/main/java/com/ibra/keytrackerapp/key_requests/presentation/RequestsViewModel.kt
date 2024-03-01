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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RequestsViewModel @Inject constructor(
    private val keyRequestUseCase: KeyRequestUseCase,
    private val profileUseCase: ProfileUseCase,
    private val tokenUseCase: TokenUseCase,
    private val logoutUseCase: LogoutUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(RequestUiState())
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                _uiState.value = _uiState.value.copy(selectedDate = LocalDate.now())
                selectWeek()
                getProfile()
                updateDayRequest()
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        dayRequests = mutableListOf(),
                        userRequests = UserRequests(
                            requests = listOf(),
                            weekStart = LocalDate.now().toString(),
                            weekEnd = LocalDate.now().toString()
                        )
                    )
                }
            }
        }
    }

    // Полученине профиля пользователя
    private suspend fun getProfile() {
        val token = getTokenIfNotExpired()
        val profileResponse = profileUseCase.getProfile(token)
        if (profileResponse.isSuccessful) {
            _uiState.update { currentState ->
                currentState.copy(
                    profile = profileResponse.body(),
                    userRequests = if (currentState.selectedWeek.isNotEmpty()) keyRequestUseCase.getUserRequests(
                        currentState.selectedWeek[0]
                    )
                    else currentState.userRequests
                )
            }
        }
    }

    private suspend fun getTokenIfNotExpired(): String {
        val token = tokenUseCase.getTokenFromLocalStorage()
        if (tokenUseCase.isTokenExpired(token)) {
            // обработать протухший токен
        }
        return token
    }

    // Выход из аккаунта
    fun logout() {
        viewModelScope.launch(Dispatchers.Default) {
            val token = getTokenIfNotExpired()
            logoutUseCase.execute(token)
        }
    }

    // Удаление заявки
    fun deleteRequest(requestId: String) {
        viewModelScope.launch(Dispatchers.Default) {
            keyRequestUseCase.deleteRequest(requestId)

            _uiState.value =
                _uiState.value.copy(userRequests = keyRequestUseCase.getUserRequests(_uiState.value.selectedWeek[0]))

            _uiState.value = _uiState.value.copy(
                dayRequests = keyRequestUseCase.getDayRequests(
                    _uiState.value.selectedDate, _uiState.value.userRequests
                )
            )
        }
    }

    // Перевод номера дня недели в его название
    fun getDayOfWeekName(dayOfWeek: Int): String {
        return keyRequestUseCase.getDayOfWeekName(dayOfWeek)
    }

    // Получение времени начала пары
    fun getPairStartTime(pairNum: Int): String {
        return keyRequestUseCase.getPairStartTime(pairNum)
    }

    // Получение времени конца пары
    fun getPairEndTime(pairNum: Int): String {
        return keyRequestUseCase.getPairEndTime(pairNum)
    }

    // Получение названия пары
    fun getPairName(keyRequestDto: KeyRequestDto): String {
        return keyRequestUseCase.getPairName(keyRequestDto)
    }

    // Выбор даты
    fun selectDate(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(selectedDate = date)
            selectWeek()
            updateDayRequest()
        }
    }

    // Обновление списка заявок
    private suspend fun updateDayRequest() {
        _uiState.update { currentState ->
            currentState.copy(
                userRequests = keyRequestUseCase.getUserRequests(_uiState.value.selectedWeek[0]),
                dayRequests = keyRequestUseCase.getDayRequests(
                    _uiState.value.selectedDate, _uiState.value.userRequests
                )
            )
        }
    }

    // Получение названия статуса заявки
    fun getRequestStatusName(keyRequestDto: KeyRequestDto): String {
        return keyRequestUseCase.getRequestStatusName(keyRequestDto)
    }

    // Получение недели, содержащей выбранную дату
    private fun selectWeek() {
        val currentDate = _uiState.value.selectedDate
        val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)
        val week: MutableList<LocalDate> = mutableListOf()

        for (i in 0..6) week.add(startOfWeek.plusDays(i.toLong()))

        _uiState.value = _uiState.value.copy(selectedWeek = week)
    }
}

// Данные о состоянии экрана
data class RequestUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedWeek: MutableList<LocalDate> = mutableListOf(),
    val dayRequests: MutableList<KeyRequestDto> = mutableListOf(),
    val userRequests: UserRequests? = null,
    val profile: Profile? = null
)