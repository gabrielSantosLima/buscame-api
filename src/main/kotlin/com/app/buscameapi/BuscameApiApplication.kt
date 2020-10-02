package com.app.buscameapi

import com.app.buscameapi.util.Properties
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableAutoConfiguration
@SpringBootApplication
class BuscameApiApplication

fun main(args: Array<String>) {
	runApplication<BuscameApiApplication>(*args)
}
