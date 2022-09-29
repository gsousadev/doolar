package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.task.enum.ActivityCategory
import com.salus.doolar.domain.task.enum.DayName
import com.salus.doolar.domain.task.enum.PeriodName
import com.salus.doolar.domain.task.valueObject.Activity
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DayTest {

    @Test
    fun testCreateDay() {
        val day = Day(DayName.MONDAY)
        assertEquals(expected = DayName.MONDAY, actual = day.name)
        assertEquals(0, day.tasks.size)
    }

    @Test
    fun testShouldAddTaskAndRemoveTask() {

        val activity = Activity("Lavar Roupa", ActivityCategory.HOME_CARE)
        val task1 = Task(1, PeriodName.MORNING, activity)
        val task2 = Task(1, PeriodName.MORNING, activity)

        Day(DayName.MONDAY).run {
            addTask(task1)
            addTask(task2)
            assertEquals(2, tasks.size)
            removeTask(task2)
            assertEquals(1, tasks.size)
            assertFalse(block = { tasks.contains(task2) })
            assertTrue(block = { tasks.contains(task1) })
        }

    }

    @Test
    fun testShouldRelocateTasksPrioritiesWhenNewTaskPriorityAlreadyExistInTaskList(){
        val task1 = Task(10, PeriodName.MORNING, Activity("Lavar Roupa", ActivityCategory.HOME_CARE))
        val task2 = Task(4, PeriodName.MORNING, Activity("Arrumar a cama", ActivityCategory.HOME_CARE))
        val task3 = Task(7, PeriodName.MORNING, Activity("Cozinhar", ActivityCategory.HOME_CARE))
        val task4 = Task(2, PeriodName.MORNING, Activity("Limpar o chão", ActivityCategory.HOME_CARE))

        Day(DayName.MONDAY).run {
            addTask(task1)
            assertEquals(1, task1.priority)
            addTask(task2)
            assertEquals(2, task2.priority)
            addTask(task3)
            assertEquals(3, task3.priority)

            addTask(task4)
            assertEquals(1, task1.priority)
            assertEquals(2, task4.priority)
            assertEquals(3, task2.priority)
            assertEquals(4, task3.priority)
        }

        val task5 = Task(24, PeriodName.MORNING, Activity("Lavar Roupa", ActivityCategory.HOME_CARE))
        val task6= Task(10, PeriodName.MORNING, Activity("Arrumar a cama", ActivityCategory.HOME_CARE))
        val task7 = Task(5, PeriodName.MORNING, Activity("Cozinhar", ActivityCategory.HOME_CARE))
        val task8 = Task(3, PeriodName.MORNING, Activity("Limpar o chão", ActivityCategory.HOME_CARE))


        Day(DayName.THURSDAY, mutableListOf(task5, task6, task7, task8)).run {
            assertEquals(4, task5.priority)
            assertEquals(3, task6.priority)
            assertEquals(2, task7.priority)
            assertEquals(1, task8.priority)
        }
    }
}