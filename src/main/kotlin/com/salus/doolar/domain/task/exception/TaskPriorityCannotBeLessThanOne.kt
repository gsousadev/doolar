package com.salus.doolar.domain.task.exception

class TaskPriorityCannotBeLessThanOne :
    Exception("A prioridade de uma tarefa precisa ser maior ou igual a 1")
