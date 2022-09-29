package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.AbstractEntity
import com.salus.doolar.domain.task.enum.DayName

class Day(
    val name: DayName,
    var tasks: MutableList<Task> = mutableListOf()
) : AbstractEntity() {

    init {
        this.normalizePriorities()
    }

    fun addTask(newTask: Task) {
        this.normalizePriorities()

        if (tasks.isEmpty()) {
            newTask.changePriority(1);
        }

        if (tasks.any { it.priority == newTask.priority }) {
            relocatePrioritiesByNewTaskPriority(newTask)
        }

        tasks.add(newTask)

        this.normalizePriorities()
    }

    fun removeTask(task: Task) {
        this.tasks.remove(task)
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
