package com.easysystems.easyorderservice

import com.easysystems.easyorderservice.data.*
import com.easysystems.easyorderservice.entities.Item
import com.easysystems.easyorderservice.repositories.*
import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EasyOrderServiceApplication

fun main(args: Array<String>) {
	runApplication<EasyOrderServiceApplication>(*args)
}
