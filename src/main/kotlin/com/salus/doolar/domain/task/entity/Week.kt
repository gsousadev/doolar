package com.salus.doolar.domain.task.entity

import com.salus.doolar.domain.AbstractEntity

class Week(
    var description: String,
    var days: List<Day>
): AbstractEntity()
{
    var completed = false
        private set
}