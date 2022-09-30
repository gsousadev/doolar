package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.task.enum.ActivityCategory
import com.salus.doolar.domain.task.enum.DayName
import com.salus.doolar.domain.task.enum.PeriodName
import com.salus.doolar.domain.task.exception.WeekCannotBeWithoutDaysException
import com.salus.doolar.domain.task.exception.WeekCannotHaveMoreThanSevenDaysException
import com.salus.doolar.domain.task.valueObject.Activity
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

internal class WeekTest {

    @Test
    fun testShouldThrowErrorWhenCreateWeekWithNumberOfDaysGreaterThanSeven() {

        val activity = Activity("Lavar Roupa", ActivityCategory.HOME_CARE)
        val task = Task(10, PeriodName.MORNING, activity)
        val day1 = Day(DayName.MONDAY, mutableListOf(task))
        val day2 = Day(DayName.TUESDAY, mutableListOf(task))
        val day3 = Day(DayName.WEDNESDAY, mutableListOf(task))
        val day4 = Day(DayName.THURSDAY, mutableListOf(task))
        val day5 = Day(DayName.FRIDAY, mutableListOf(task))
        val day6 = Day(DayName.SATURDAY, mutableListOf(task))
        val day7 = Day(DayName.SUNDAY, mutableListOf(task))
        val day8 = Day(DayName.TEST, mutableListOf(task))

        assertThrows<WeekCannotHaveMoreThanSevenDaysException> {
            Week(
                "Semana de férias", mutableMapOf(
                    day1.name to day1,
                    day2.name to day2,
                    day3.name to day3,
                    day4.name to day4,
                    day5.name to day5,
                    day6.name to day6,
                    day7.name to day7,
                    day8.name to day8,
                )
            )
        }

    }
    @Test
    fun testShouldThrowErrorWhenCreateWeekWithoutDays() {

        assertThrows<WeekCannotBeWithoutDaysException> {
            Week(
                "Semana de férias", mutableMapOf()
            )
        }
    }

    @Test
    fun testShouldAddDay(){

        val week = Week("Semana de testes", mutableMapOf())
    }
}