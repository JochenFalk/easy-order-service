package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.services.SessionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/mollieWebhook")
class MollieWebhookController(val sessionService: SessionService) {

    @PostMapping
    fun updateSessionStatus(@RequestParam("id") id: String) {
        return sessionService.updateSessionStatus(id)
    }
}