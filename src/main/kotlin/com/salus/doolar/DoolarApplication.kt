package com.salus.doolar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DoolarApplication

fun main(args: Array<String>) {
	runApplication<DoolarApplication>(*args)
}
