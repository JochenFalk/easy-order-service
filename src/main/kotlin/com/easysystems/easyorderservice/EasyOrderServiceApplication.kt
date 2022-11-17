package com.easysystems.easyorderservice

import com.easysystems.easyorderservice.services.SessionService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EasyOrderServiceApplication

fun main(args: Array<String>) {
	runApplication<EasyOrderServiceApplication>(*args)
}
