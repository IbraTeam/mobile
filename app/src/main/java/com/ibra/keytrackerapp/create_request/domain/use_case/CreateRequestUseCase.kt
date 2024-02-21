package com.ibra.keytrackerapp.create_request.domain.use_case

import com.ibra.keytrackerapp.create_request.domain.model.FreeKey
import java.time.LocalDate

class CreateRequestUseCase {

    // Получение списка дней для быстрого выбора
    fun getDatesRow(selectedDate: LocalDate) : List<LocalDate> {
        return listOf(
            selectedDate.minusDays(1),
            selectedDate,
            selectedDate.plusDays(1)
        )
    }

    // Генерация списка свободных ключей
    fun generageFreeKeys() : List<FreeKey> {
        return listOf(
            FreeKey(
                name = "302",
                keyId = "1"
            ),
            FreeKey(
                name = "401",
                keyId = "2"
            ),
            FreeKey(
                name = "101",
                keyId = "3"
            ),
            FreeKey(
                name = "13",
                keyId = "4"
            ),
            FreeKey(
                name = "1212",
                keyId = "6"
            ),
        )
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
}