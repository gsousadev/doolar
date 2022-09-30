package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.AbstractEntity
import com.salus.doolar.domain.task.enum.ActivityCategory
import com.salus.doolar.domain.task.enum.PeriodName
import com.salus.doolar.domain.task.enum.TaskStatus
import com.salus.doolar.domain.task.exception.FinishedTaskCannotBeStatusChangedToNotFinishedException
import com.salus.doolar.domain.task.exception.NotFinishedTaskCannotBeStatusChangedException
import com.salus.doolar.domain.task.exception.TaskPriorityCannotBeLessThanOneException
import com.salus.doolar.domain.task.valueObject.Activity

class Task(
    priority: Int,
    period: PeriodName,
    private val activity: Activity
) : AbstractEntity() {

    init {
        validateNewPriority(priority);
    }

    var priority: Int = priority
        private set(value){
            validateNewPriority(value)
            field = value
        }

    var period = period
        private set

    var status = TaskStatus.PENDING
        private set

    fun finish() {
        this.changeStatus(TaskStatus.FINISHED)
    }

    fun reset() {
        this.changeStatus(TaskStatus.PENDING)
    }

    fun markTaskAsNotFinished() {
        this.changeStatus(TaskStatus.NOT_FINISHED)
    }

    fun changePeriod(newPeriod: PeriodName) {
        this.period = newPeriod
    }

    fun changePriority(newPriority: Int) {
        this.priority = newPriority
    }

    fun getDescription(): String {
        return this.activity.description
    }

    fun getCategory(): ActivityCategory {
        return this.activity.category
    }

    private fun changeStatus(newStatus: TaskStatus) {
        if (this.status == TaskStatus.NOT_FINISHED) {
            throw NotFinishedTaskCannotBeStatusChangedException()
        }

        if (newStatus == TaskStatus.NOT_FINISHED && this.status == TaskStatus.FINISHED) {
            throw FinishedTaskCannotBeStatusChangedToNotFinishedException()
        }

        this.status = newStatus
    }

    private fun validateNewPriority(newPriority: Int) {
        if (newPriority <= 0) {
            throw TaskPriorityCannotBeLessThanOneException()
        }
    }
}


