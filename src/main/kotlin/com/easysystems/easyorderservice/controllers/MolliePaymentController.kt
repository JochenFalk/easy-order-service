package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.MolliePaymentDTO
import com.easysystems.easyorderservice.services.MolliePaymentService
import com.easysystems.easyorderservice.services.SessionService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/molliePayments")
@Validated
class MolliePaymentController(val molliePaymentService: MolliePaymentService, val sessionService: SessionService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMolliePayment(@RequestBody @Valid molliePaymentDTO: MolliePaymentDTO): MolliePaymentDTO {
        return molliePaymentService.createMolliePayment(molliePaymentDTO)
    }

    @GetMapping("/{mollieId}")
    fun retrieveMolliePaymentByMollieId(@PathVariable("mollieId") mollieId: Int): MolliePaymentDTO {
        return molliePaymentService.retrieveMolliePaymentById(mollieId)
    }

    @GetMapping
    fun retrieveAllMolliePayments(): ArrayList<MolliePaymentDTO> {
        return molliePaymentService.retrieveAllMolliePayments()
    }

    @PutMapping("/{mollieId}")
    fun updateMolliePayment(
        @RequestBody @Valid molliePaymentDTO: MolliePaymentDTO,
        @PathVariable("mollieId") mollieId: Int
    ): MolliePaymentDTO {
        return molliePaymentService.updateMolliePayment(mollieId, molliePaymentDTO)
    }

    @DeleteMapping("/{mollieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMolliePayment(@PathVariable("mollieId") mollieId: Int) {
        return molliePaymentService.deleteMolliePayment(mollieId)
    }
}