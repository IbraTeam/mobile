package com.ibra.keytrackerapp.key_requests.domain.use_case

import com.ibra.keytrackerapp.key_requests.domain.model.KeyRequestDto
import com.ibra.keytrackerapp.key_requests.domain.model.RequestStatus
import com.ibra.keytrackerapp.key_requests.domain.model.RequestType
import com.ibra.keytrackerapp.key_requests.domain.model.UserDto
import com.ibra.keytrackerapp.key_requests.domain.model.UserRole
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class KeyRequestUseCase() {

    // Генерация заявок пользователя
    fun generateRequests() : MutableList<KeyRequestDto> {
        var requests = mutableListOf<KeyRequestDto>()

        requests.add(
            KeyRequestDto(
                id = "1",
                name = "332(2)",
                pairName = "",
                status = RequestStatus.Pending,
                dateTime = LocalDate.now().toString(),
                dayNumb = 3,
                repeated = false,
                typeBooking = RequestType.Booking,
                pairNumber = 0,
                keyId = "1",
                user = UserDto(
                    id = "1",
                    name = "Змеев Олег Алексеевич",
                    email = "oleg@alekseevich.com",
                    role = UserRole.Teacher
                )
            )
        )

        requests.add(
            KeyRequestDto(
                id = "2",
                name = "223(2)",
                pairName = "Программирование",
                status = RequestStatus.Accepted,
                dateTime = LocalDate.now().toString(),
                dayNumb = 3,
                repeated = false,
                typeBooking = RequestType.Pair,
                pairNumber = 1,
                keyId = "2",
                user = UserDto(
                    id = "1",
                    name = "Змеев Олег Алексеевич",
                    email = "oleg@alekseevich.com",
                    role = UserRole.Teacher
                )
            )
        )

        requests.add(
            KeyRequestDto(
                id = "3",
                name = "123(2)",
                pairName = "",
                status = RequestStatus.Rejected,
                dateTime = LocalDate.now().toString(),
                dayNumb = 3,
                repeated = false,
                typeBooking = RequestType.Booking,
                pairNumber = 3,
                keyId = "3",
                user = UserDto(
                    id = "1",
                    name = "Змеев Олег Алексеевич",
                    email = "oleg@alekseevich.com",
                    role = UserRole.Teacher
                )
            )
        )

        return requests
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
    fun getDayRequests(date : LocalDate, keyRequestList : MutableList<KeyRequestDto>) : MutableList<KeyRequestDto> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val newList = keyRequestList.toMutableList()
        newList.removeIf { LocalDate.parse(it.dateTime, formatter) != date }

        return newList
    }
}