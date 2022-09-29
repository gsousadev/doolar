package com.salus.doolar.domain

import java.util.UUID

abstract class AbstractEntity {
    val id = UUID.randomUUID()
}