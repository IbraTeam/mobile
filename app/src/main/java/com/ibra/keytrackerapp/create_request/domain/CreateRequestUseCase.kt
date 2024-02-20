package com.ibra.keytrackerapp.create_request.domain

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
}