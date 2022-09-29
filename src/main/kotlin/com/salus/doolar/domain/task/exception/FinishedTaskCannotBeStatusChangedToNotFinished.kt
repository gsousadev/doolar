package com.salus.doolar.domain.task.exception

class FinishedTaskCannotBeStatusChangedToNotFinished :
    Exception("Tasks com status FINALIZADO não podem ter o status trocado para NÃO FEITAS")
