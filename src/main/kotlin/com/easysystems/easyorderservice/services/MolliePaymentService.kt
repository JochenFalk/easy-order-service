package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.*
import com.easysystems.easyorderservice.entities.MolliePayment
import com.easysystems.easyorderservice.exceptions.PaymentNotFoundException
import com.easysystems.easyorderservice.exceptions.SessionNotValidException
import com.easysystems.easyorderservice.repositories.MolliePaymentRepository
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class MolliePaymentService(
    val molliePaymentRepository: MolliePaymentRepository,
    val sessionRepository: SessionRepository
) {

    companion object : KLogging()

    fun createMolliePayment(molliePaymentDTO: MolliePaymentDTO): MolliePaymentDTO {

        // Using repo instead of service to circumvent circular reference warning between session-
        // and molliePayment-service
        val sessionOptional = sessionRepository.findById(molliePaymentDTO.sessionId!!)

        if(!sessionOptional.isPresent){
            throw SessionNotValidException("Session is not valid for given id: ${molliePaymentDTO.sessionId}")
        }

        val molliePayment = molliePaymentDTO.let {

            MolliePayment(it.molliePaymentId, it.amount, it.createdAt, it.description, it.expiresAt, it.id, it.isCancelable,
                it.method, it.mode, it.profileId, it.checkoutUrl, it.redirectUrl, it.webhookUrl, it.resource, it.sequenceType, it.status,
                sessionOptional.get())
        }

        molliePaymentRepository.save(molliePayment)

        logger.info("New mollie payment created: $molliePayment")

        return molliePayment.let {

            MolliePaymentDTO(it.molliePaymentId, it.amount, it.createdAt, it.description, it.expiresAt, it.id, it.isCancelable,
                it.method, it.mode, it.profileId, it.checkoutUrl, it.redirectUrl, it.webhookUrl, it.resource, it.sequenceType, it.status,
                it.session?.id)
        }
    }

    fun retrieveMolliePaymentById(id: Int): MolliePaymentDTO {

        val molliePayment = molliePaymentRepository.findById(id)

        return if (molliePayment.isPresent) {
            molliePayment.get().let {

                MolliePaymentDTO(it.molliePaymentId, it.amount, it.createdAt, it.description, it.expiresAt, it.id, it.isCancelable,
                    it.method, it.mode, it.profileId, it.checkoutUrl, it.redirectUrl, it.webhookUrl, it.resource, it.sequenceType, it.status,
                    it.session?.id)
            }
        } else {
            throw PaymentNotFoundException("No mollie payment found for given id: $id")
        }
    }

    fun retrieveMolliePaymentByMolliePaymentId(mollieId: String): MolliePaymentDTO {

        val molliePayment = molliePaymentRepository.findByMollieId(mollieId)

        return molliePayment?.let {

            MolliePaymentDTO(it.molliePaymentId, it.amount, it.createdAt, it.description, it.expiresAt, it.id, it.isCancelable,
                it.method, it.mode, it.profileId, it.checkoutUrl, it.redirectUrl, it.webhookUrl, it.resource, it.sequenceType, it.status,
                it.session?.id)
        }
            ?: throw PaymentNotFoundException("No mollie payment found for given id. " +
                    "Payment might be overridden when method was changed: " +
                    "$mollieId")
    }

    fun retrieveAllMolliePayments(): ArrayList<MolliePaymentDTO> {

        return molliePaymentRepository.findAll()
            .map {

                MolliePaymentDTO(it.molliePaymentId, it.amount, it.createdAt, it.description, it.expiresAt, it.id, it.isCancelable,
                    it.method, it.mode, it.profileId, it.checkoutUrl, it.redirectUrl, it.webhookUrl, it.resource, it.sequenceType, it.status,
                    it.session?.id)

            } as ArrayList<MolliePaymentDTO>
    }

    fun updateMolliePayment(id: Int, molliePaymentDTO: MolliePaymentDTO): MolliePaymentDTO {

        val molliePayment = molliePaymentRepository.findById(id)

        return if (molliePayment.isPresent) {
            molliePayment.get().let {

                it.status = molliePaymentDTO.status

                molliePaymentRepository.save(it)

                MolliePaymentDTO(it.molliePaymentId, it.amount, it.createdAt, it.description, it.expiresAt, it.id, it.isCancelable,
                    it.method, it.mode, it.profileId, it.checkoutUrl, it.redirectUrl, it.webhookUrl, it.resource, it.sequenceType, it.status,
                    it.session?.id)
            }
        } else {
            throw PaymentNotFoundException("No mollie payment found for given id: $id")
        }
    }

    fun deleteMolliePayment(id: Int) {

        val molliePayment = molliePaymentRepository.findById(id)

        if (molliePayment.isPresent) {
            molliePayment.get().let {
                molliePaymentRepository.deleteById(id)
            }
        } else {
            throw PaymentNotFoundException("No mollie payment found for given id: $id")
        }
    }
}