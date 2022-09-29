package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.task.enum.ActivityCategory
import com.salus.doolar.domain.task.enum.PeriodName
import com.salus.doolar.domain.task.enum.TaskStatus
import com.salus.doolar.domain.task.exception.FinishedTaskCannotBeStatusChangedToNotFinished
import com.salus.doolar.domain.task.exception.NotFinishedTaskCannotBeStatusChangedException
import com.salus.doolar.domain.task.exception.TaskPriorityCannotBeLessThanOne
import com.salus.doolar.domain.task.valueObject.Activity
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TaskTest {

    private val activity = Activity("Lavar Roupa", ActivityCategory.HOME_CARE)

    @Test()
    fun testShouldChangeStatus() {
        Task(1, PeriodName.MORNING, this.activity).run {
            assertEquals(TaskStatus.PENDING, this.status)
            this.finish()
            assertEquals(TaskStatus.FINISHED, this.status)
            this.reset()
            assertEquals(TaskStatus.PENDING, this.status)
        }
    }

    @Test
    fun testShouldThrowWhenCompletedStatusIsChangedToNotFinishedStatus() {
        Task(1, PeriodName.MORNING, this.activity).run {
            assertThrows<FinishedTaskCannotBeStatusChangedToNotFinished> {
                this.finish()
                this.markTaskAsNotFinished()
            }
        }
    }

    @Test
    fun testShouldThrowWhenNotFinishedStatusIsChangedToOtherStatus(){
        Task(1, PeriodName.MORNING, this.activity).run {
            assertThrows<NotFinishedTaskCannotBeStatusChangedException> {
                this.markTaskAsNotFinished()
                this.reset()
            }
        }
    }

    @Test
    fun testShouldChangePeriod(){
        Task(1, PeriodName.MORNING, this.activity).run {
            assertEquals(PeriodName.MORNING, this.period)
            this.changePeriod(PeriodName.AFTERNOON)
            assertEquals(PeriodName.AFTERNOON, this.period)
        }
    }

    @Test
    fun testShouldChangePriority(){
        Task(1, PeriodName.MORNING, this.activity).run {
            assertEquals(1, this.priority)
            this.changePriority(2)
            assertEquals(2, this.priority)
        }
    }

    @Test
    fun testShouldThrowWhenNewPriorityIsLessThanOne(){
        Task(1, PeriodName.MORNING, this.activity).run {
            assertThrows<TaskPriorityCannotBeLessThanOne> {
                this.changePriority(0)
            }
        }

        assertThrows<TaskPriorityCannotBeLessThanOne> {
            Task(-2, PeriodName.MORNING, this.activity)
        }
    }

    @Test()
    fun testShouldGetActivityDescriptionAndActivityCategory() {
        Task(1, PeriodName.MORNING, this.activity).run {
            assertEquals("Lavar Roupa", this.getDescription())
            assertEquals(ActivityCategory.HOME_CARE, this.getCategory())
        }
    }
}