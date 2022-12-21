package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.AbstractEntity
import com.salus.doolar.domain.task.enum.DayName
import com.salus.doolar.domain.task.exception.WeekCannotBeWithoutDaysException
import com.salus.doolar.domain.task.exception.WeekCannotHaveMoreThanSevenDaysException

data class Week(
    val description: String,
    val days: MutableMap<DayName, Day>
) : AbstractEntity() {

    init {
        this.validateWeekDays()
    }

    private fun validateWeekDays() {
        if (days.size > 7) {
            throw WeekCannotHaveMoreThanSevenDaysException()
        }

        if (days.isEmpty()) {
            throw WeekCannotBeWithoutDaysException()
        }
    }

    fun addDay(day: Day) {
        this.days[day.name] = day
    }

    fun removeDay(day: Day) {
        this.days.remove(day.name)
    }
}