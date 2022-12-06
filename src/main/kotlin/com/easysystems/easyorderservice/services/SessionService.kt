package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.MolliePaymentDTO
import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.MolliePayment
import com.easysystems.easyorderservice.entities.Session
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.exceptions.SessionNotFoundException
import com.easysystems.easyorderservice.repositories.MolliePaymentRepository
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
import org.apache.commons.logging.Log
import org.springframework.stereotype.Service

@Service
class SessionService(
    val sessionRepository: SessionRepository,
    val molliePaymentService: MolliePaymentService,
    val tabletopService: TabletopService,
    val orderService: OrderService,
    val authenticationService: AuthenticationService
) {

    companion object : KLogging()

    fun createSession(sessionDTO: SessionDTO): SessionDTO {

        val session = sessionDTO.let {

            val tabletop = Tabletop(it.tabletopDTO!!.id, it.tabletopDTO!!.authCode)

            Session(null, it.status.toString(), tabletop)
        }

        sessionRepository.save(session)

        logger.info("New session created: $session")

        return session.let {

            val tabletopDTO = tabletopService.retrieveTabletopById(it.tabletop!!.id!!)

            SessionDTO(it.id, SessionDTO.Status.valueOf(it.status!!), tabletopDTO, it.total)
        }
    }

    fun retrieveSessionById(id: Int): SessionDTO {

        val session = sessionRepository.findById(id)

        return if (session.isPresent) {
            session.get().let {

                val tabletopDTO = tabletopService.retrieveTabletopById(it.tabletop!!.id!!)
                val ordersDTO = ArrayList<OrderDTO>()

                for (o in it.orders) {
                    orderService.retrieveOrderById(o.id!!).let { orderDTO ->
                        ordersDTO.add(orderDTO)
                    }
                }

                val molliePaymentDTO = it.payment?.molliePaymentId?.let { molliePaymentId ->
                    molliePaymentService.retrieveMolliePaymentById(molliePaymentId)
                }

                SessionDTO(
                    it.id,
                    SessionDTO.Status.valueOf(it.status!!),
                    tabletopDTO,
                    it.total,
                    ordersDTO,
                    molliePaymentDTO
                )
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }

    fun retrieveAllSessions(): ArrayList<SessionDTO> {

        return sessionRepository.findAll()
            .map {

                val tabletopDTO = tabletopService.retrieveTabletopById(it.tabletop!!.id!!)
                val ordersDTO = ArrayList<OrderDTO>()

                for (o in it.orders) {
                    orderService.retrieveOrderById(o.id!!).let { orderDTO ->
                        ordersDTO.add(orderDTO)
                    }
                }

                val molliePaymentDTO = it.payment?.molliePaymentId?.let { molliePaymentId ->
                    molliePaymentService.retrieveMolliePaymentById(molliePaymentId)
                }

                SessionDTO(
                    it.id,
                    SessionDTO.Status.valueOf(it.status!!),
                    tabletopDTO,
                    it.total,
                    ordersDTO,
                    molliePaymentDTO
                )

            } as ArrayList<SessionDTO>
    }

    fun updateSession(id: Int, sessionDTO: SessionDTO): SessionDTO {

        val session = sessionRepository.findById(id)
        val tabletop = sessionDTO.tabletopDTO?.authCode?.let { authCode ->
            Tabletop(sessionDTO.tabletopDTO?.id, authCode)
        }

        return if (session.isPresent) {
            session.get().let {

                it.status = sessionDTO.status.toString()
                it.tabletop = tabletop
                it.total = sessionDTO.total

                if (sessionDTO.orders != null) {
                    for (o in sessionDTO.orders!!) {
                        orderService.updateOrder(o.id!!, o)
                    }
                }

                sessionDTO.payment?.molliePaymentId?.let { molliePaymentId ->
                    molliePaymentService.updateMolliePayment(
                        molliePaymentId,
                        sessionDTO.payment!!
                    )
                }

                sessionRepository.save(it)

                val tabletopDTO = tabletop?.id?.let { tabletopId -> tabletopService.retrieveTabletopById(tabletopId) }
                val ordersDTO = ArrayList<OrderDTO>()

                for (o in it.orders) {
                    orderService.retrieveOrderById(o.id!!).let { orderDTO ->
                        ordersDTO.add(orderDTO)
                    }
                }

                val molliePaymentDTO = it.payment?.molliePaymentId?.let { molliePaymentId ->
                    molliePaymentService.retrieveMolliePaymentById(molliePaymentId)
                }

                SessionDTO(
                    it.id,
                    SessionDTO.Status.valueOf(it.status!!),
                    tabletopDTO,
                    it.total,
                    ordersDTO,
                    molliePaymentDTO
                )
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }

    fun deleteSession(id: Int) {

        val session = sessionRepository.findById(id)

        if (session.isPresent) {
            session.get().let {
                sessionRepository.deleteById(id)
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }

    fun retrieveSessionByTabletop(tabletopId: Int, authCode: String): SessionDTO? {

        val isVerified = authenticationService.authenticateTableCode(tabletopId, authCode)

        if (isVerified) {

            val sessionByTabletopId = sessionRepository.findByTabletopId(tabletopId, "OPENED")

            return if (sessionByTabletopId != null) {

                logger.info("Session found: $sessionByTabletopId")
                sessionByTabletopId.id?.let { retrieveSessionById(it) }

            } else {

                val tabletopDTO = TabletopDTO(tabletopId, authCode)
                val sessionDTO = SessionDTO(tabletopDTO = tabletopDTO)

                createSession(sessionDTO)
            }
        } else {
            return null
        }
    }

    fun retrieveSessionByMollieId(mollieId: Int): SessionDTO? {

        logger.info("Webhook from mollie received: $mollieId")

        val sessionByMollieId = sessionRepository.findByMollieId(mollieId)

        return if (sessionByMollieId != null) {

            logger.info("Session found: $sessionByMollieId")
            sessionByMollieId.id?.let { retrieveSessionById(it) }

        } else {

            logger.info("No session found for Mollie id: $mollieId")
            return null
        }
    }
}
