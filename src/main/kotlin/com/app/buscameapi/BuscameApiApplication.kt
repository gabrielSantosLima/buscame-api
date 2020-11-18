package com.app.buscameapi

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableAutoConfiguration
@SpringBootApplication
class BuscameApiApplication

fun main(args: Array<String>) {
	runApplication<BuscameApiApplication>(*args)
}
