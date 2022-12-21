package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.AbstractEntity
import com.salus.doolar.domain.task.enum.DayName
import com.salus.doolar.domain.task.enum.TaskStatus

class WeeklyReport(private val week: Week) : AbstractEntity() {
    fun getNumberOfFinishedTasks(): Int {
        var tasks = 0
        week.days.forEach { (_, day: Day) ->
            tasks += day.tasks.count { it.isFinished() }
        }

        return tasks
    }

    fun getNumberOfUnfinishedTasks(): Int {
        var tasks = 0
        week.days.forEach { (_, day: Day) ->
            tasks += day.tasks.count { !it.isFinished() }
        }
        return tasks
    }

    fun getNumberOfTasks(): Int {
        var tasks = 0
        week.days.forEach { (_, day: Day) -> tasks += day.tasks.count() }
        return tasks
    }

    fun getWeekDaysListWithNumberOfTasksByStatus(): MutableMap<DayName, Map<TaskStatus, Int>> {
        val weekDays = mutableMapOf<DayName, Map<TaskStatus, Int>>()

        week.days.forEach { (_, day) ->
            weekDays[day.name] =
                mapOf<TaskStatus, Int>(
                    TaskStatus.FINISHED to day.tasks.count { it.isFinished() },
                    TaskStatus.NOT_FINISHED to day.tasks.count { it.isNotFinished() },
                    TaskStatus.PENDING to day.tasks.count { it.isPending() },
                )
        }

        return weekDays
    }
}