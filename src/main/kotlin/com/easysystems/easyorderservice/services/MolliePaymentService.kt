package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.MolliePaymentDTO
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

        val molliePayment = molliePaymentDTO.convertToMolliePayment(session = sessionOptional.get())

        molliePaymentRepository.save(molliePayment)
        logger.info("New mollie payment created: $molliePayment")

        return molliePayment.convertToMolliePaymentDTO()
    }

    fun retrieveMolliePaymentById(id: Int): MolliePaymentDTO {

        val molliePayment = molliePaymentRepository.findById(id)

        return if (molliePayment.isPresent) {
            molliePayment.get().convertToMolliePaymentDTO()
        } else {
            throw PaymentNotFoundException("No mollie payment found for given id: $id")
        }
    }

    fun retrieveMolliePaymentByMolliePaymentId(mollieId: String): MolliePaymentDTO {

        val molliePayment = molliePaymentRepository.findByMollieId(mollieId)

        return molliePayment?.convertToMolliePaymentDTO()
            ?: throw PaymentNotFoundException("No mollie payment found for given id: $mollieId " +
                    "Payment might have been overridden when payment method was changed")
    }

    fun retrieveAllMolliePayments(): ArrayList<MolliePaymentDTO> {

        return molliePaymentRepository.findAll()
            .map { it.convertToMolliePaymentDTO()
            } as ArrayList<MolliePaymentDTO>
    }

    fun updateMolliePayment(id: Int, molliePaymentDTO: MolliePaymentDTO): MolliePaymentDTO {

        val molliePayment = molliePaymentRepository.findById(id)

        return if (molliePayment.isPresent) {
            molliePayment.get().let {

                it.status = molliePaymentDTO.status
                molliePaymentRepository.save(it)

                it.convertToMolliePaymentDTO()
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