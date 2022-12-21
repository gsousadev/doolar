package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.task.enum.ActivityCategory
import com.salus.doolar.domain.task.enum.DayName
import com.salus.doolar.domain.task.enum.PeriodName
import com.salus.doolar.domain.task.enum.TaskStatus
import com.salus.doolar.domain.task.valueObject.Activity
import kotlin.test.Test
import kotlin.test.assertEquals

internal class WeekReportTest {

    @Test
    fun testShouldGetNumberOfFinishedTasks() {
        val week = this.getFilledWeek()
        val weeklyReport = WeeklyReport(week)

        assertEquals(0, weeklyReport.getNumberOfFinishedTasks())

        week.days[DayName.THURSDAY]?.tasks?.forEach { it.finish() }
        week.days[DayName.FRIDAY]?.tasks?.forEach { it.finish() }

        assertEquals(2, weeklyReport.getNumberOfFinishedTasks())
    }

    @Test
    fun testShouldGetNumberOfUnfinishedTasks() {
        val week = this.getFilledWeek()
        val weeklyReport = WeeklyReport(week)

        assertEquals(7, weeklyReport.getNumberOfUnfinishedTasks())

        week.days[DayName.THURSDAY]?.tasks?.forEach { it.finish() }
        week.days[DayName.FRIDAY]?.tasks?.forEach { it.finish() }

        assertEquals(5, weeklyReport.getNumberOfUnfinishedTasks())
    }

    @Test
    fun testShouldGetNumberOfTasks() {
        val week = this.getFilledWeek()
        val weeklyReport = WeeklyReport(week)

        assertEquals(7, weeklyReport.getNumberOfTasks())

        week.days[DayName.THURSDAY]?.tasks?.removeFirst()

        assertEquals(6, weeklyReport.getNumberOfTasks())
    }

    @Test
    fun testShouldGetWeekDaysListWithNumberOfTasksByStatus() {
        val activity = Activity("Lavar Roupa", ActivityCategory.HOME_CARE)
        val task1 = Task(10, PeriodName.MORNING, activity)
        val task2 = Task(10, PeriodName.MORNING, activity)
        val task3 = Task(10, PeriodName.MORNING, activity)
        val day1 = Day(DayName.MONDAY, mutableListOf(task1, task2, task3))
        val week = Week(
            "Semana de férias", mutableMapOf(
                day1.name to day1,
            )
        )
        task1.markTaskAsNotFinished()
        task2.finish()

        val weeklyReport = WeeklyReport(week)

        val firstWeeklyReportsByStatusResult
        = weeklyReport.getWeekDaysListWithNumberOfTasksByStatus()

        assertEquals(1, firstWeeklyReportsByStatusResult[DayName.MONDAY]?.get(TaskStatus.FINISHED))
        assertEquals(1, firstWeeklyReportsByStatusResult[DayName.MONDAY]?.get(TaskStatus.NOT_FINISHED))
        assertEquals(1, firstWeeklyReportsByStatusResult[DayName.MONDAY]?.get(TaskStatus.PENDING))

        task2.reset()

        val secondWeeklyReportsByStatusResult
                = weeklyReport.getWeekDaysListWithNumberOfTasksByStatus()

        assertEquals(0, secondWeeklyReportsByStatusResult[DayName.MONDAY]?.get(TaskStatus.FINISHED))
        assertEquals(1, secondWeeklyReportsByStatusResult[DayName.MONDAY]?.get(TaskStatus.NOT_FINISHED))
        assertEquals(2, secondWeeklyReportsByStatusResult[DayName.MONDAY]?.get(TaskStatus.PENDING))
    }


    private fun getFilledWeek(): Week {
        val day1 = Day(DayName.MONDAY, createListWithOneTask())
        val day2 = Day(DayName.TUESDAY, createListWithOneTask())
        val day3 = Day(DayName.WEDNESDAY, createListWithOneTask())
        val day4 = Day(DayName.THURSDAY, createListWithOneTask())
        val day5 = Day(DayName.FRIDAY, createListWithOneTask())
        val day6 = Day(DayName.SATURDAY, createListWithOneTask())
        val day7 = Day(DayName.SUNDAY, createListWithOneTask())

        return Week(
            "Semana de férias", mutableMapOf(
                day1.name to day1,
                day2.name to day2,
                day3.name to day3,
                day4.name to day4,
                day5.name to day5,
                day6.name to day6,
                day7.name to day7
            )
        )
    }

    private fun createListWithOneTask(): MutableList<Task> {
        val activity = Activity("Lavar Roupa", ActivityCategory.HOME_CARE)
        return mutableListOf(Task(10, PeriodName.MORNING, activity))
    }
}