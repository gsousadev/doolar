package com.salus.doolar.domain.task.exception

class NotFinishedTaskCannotBeStatusChangedException:
    Exception("Tarefas não concluídas não podem ter o status alterado novamente")