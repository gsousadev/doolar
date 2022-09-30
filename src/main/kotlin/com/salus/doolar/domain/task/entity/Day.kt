package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.AbstractEntity
import com.salus.doolar.domain.task.enum.DayName
import com.salus.doolar.domain.task.exception.DayCannotBeWithoutTasksException

data class Day(
    val name: DayName,
    val tasks: MutableList<Task>
) : AbstractEntity() {

    init {
        this.validateTaskList()
        this.normalizePriorities()
    }

    private fun validateTaskList() {
        if (this.tasks.isEmpty()) {
            throw DayCannotBeWithoutTasksException()
        }
    }

    fun addTask(newTask: Task) {
        this.normalizePriorities()

        if (tasks.any { it.priority == newTask.priority }) {
            relocatePrioritiesByNewTaskPriority(newTask)
        }

        tasks.add(newTask)

        this.normalizePriorities()
    }

    fun removeTask(task: Task) {
        this.tasks.remove(task)
        this.validateTaskList()
        this.normalizePriorities()
    }

    private fun relocatePrioritiesByNewTaskPriority(firstTaskToRelocate: Task) {
        tasks.filter { it.priority >= firstTaskToRelocate.priority }
            .forEach {
                it.changePriority(it.priority + 1)
            }
    }

    private fun normalizePriorities() {
        tasks.sortWith(compareBy { it.priority });
        tasks.forEach { it.changePriority(tasks.indexOf(it) + 1) }
    }
}
