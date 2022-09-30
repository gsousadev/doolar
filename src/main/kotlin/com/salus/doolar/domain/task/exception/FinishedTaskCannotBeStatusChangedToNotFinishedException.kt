package com.salus.doolar.domain.task.exception

class FinishedTaskCannotBeStatusChangedToNotFinishedException :
    Exception("Tasks com status FINALIZADO não podem ter o status trocado para NÃO FEITAS")
