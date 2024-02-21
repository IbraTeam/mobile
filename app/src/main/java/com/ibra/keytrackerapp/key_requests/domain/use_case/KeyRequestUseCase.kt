package com.ibra.keytrackerapp.key_requests.domain.use_case

import com.ibra.keytrackerapp.common.token.domain.usecase.TokenUseCase
import com.ibra.keytrackerapp.key_requests.domain.enums.PairNumber
import com.ibra.keytrackerapp.key_requests.domain.model.KeyRequestDto
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestStatus
import com.ibra.keytrackerapp.key_requests.domain.enums.RequestType
import com.ibra.keytrackerapp.key_requests.domain.model.UserDto
import com.ibra.keytrackerapp.key_requests.domain.enums.UserRole
import com.ibra.keytrackerapp.key_requests.domain.model.UserRequests
import com.ibra.keytrackerapp.key_requests.domain.repository.RequestRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class KeyRequestUseCase @Inject constructor(
    private val requestRepository: RequestRepository,
    private val tokenUseCase: TokenUseCase
) {

    // Получение всех заявок пользователя
    suspend fun getUserRequests(weekStart: LocalDate) : UserRequests {
        val formatter = DateTimeFormatter.ofPattern("MM.dd.yyyy")
        val weekStartStr = weekStart.format(formatter)

        return requestRepository.getUserRequests("Bearer ${tokenUseCase.getTokenFromLocalStorage()}", weekStartStr)
    }

    // Удаление заявки
    suspend fun deleteRequest(requestId: String) {
        val token = "Bearer ${tokenUseCase.getTokenFromLocalStorage()}"
        requestRepository.deleteRequest(token, requestId)
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

    // Получение времени начала пары
    fun getPairStartTime(pairNum : Int) : String {
        return when (pairNum) {
            0 -> "8.45"
            1 -> "10.35"
            2 -> "12.25"
            3 -> "14.45"
            4 -> "16.35"
            5 -> "18.25"
            6 -> "20.15"
            else -> ""
        }
    }

    // Получение времени конца пары
    fun getPairEndTime(pairNum : Int) : String {
        return when (pairNum) {
            0 -> "10.20"
            1 -> "12.10"
            2 -> "14.00"
            3 -> "16.20"
            4 -> "18.10"
            5 -> "20.00"
            6 -> "21.50"
            else -> ""
        }
    }

    // Получение названия пары
    fun getPairName(keyRequestDto: KeyRequestDto) : String {
        return when (keyRequestDto.typeBooking) {
            RequestType.Pair -> keyRequestDto.pairName
            RequestType.Booking -> "Бронь"
        }
    }

    // Получение названия статуса заявки
    fun getRequestStatusName(keyRequestDto: KeyRequestDto) : String {
        return when(keyRequestDto.status) {
            RequestStatus.Rejected -> "Отклонена"
            RequestStatus.Pending -> "На рассмотрении"
            RequestStatus.Accepted -> "Подтверждена"
        }
    }

    // Получение всех заявок на день
    fun getDayRequests(date : LocalDate, keyRequestList : List<KeyRequestDto>) : MutableList<KeyRequestDto> {
        if (keyRequestList == null || keyRequestList.isEmpty())
            return mutableListOf()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val newList = keyRequestList.toMutableList()
        newList.removeIf { LocalDate.parse(it.dateTime.slice(0..9), formatter) != date }

        return newList
    }
}