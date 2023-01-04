package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.MolliePaymentDTO
import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.MolliePayment
import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.exceptions.SessionNotFoundException
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
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

            val tabletop = it.tabletop!!.convertToTabletop(id = null)
            val orders = ArrayList<Order>()
            val payments = ArrayList<MolliePayment>()

            it.convertToSession(id = null, tabletop = tabletop, orders = orders, payments = payments)
        }

        sessionRepository.save(session)
        logger.info("New session created: $session")

        return session.let {

            val tabletopDTO = tabletopService.retrieveTabletopById(it.tabletop!!.id!!)
            val ordersDTO = getOrdersAsDTO(it.orders)
            val paymentsDTO = getPaymentsAsDTO(it.payments)

            it.convertToSessionDTO(tabletopDTO, ordersDTO, paymentsDTO)
        }
    }

    fun retrieveSessionById(id: Int): SessionDTO {

        val sessionToReturn = sessionRepository.findById(id)

        return if (sessionToReturn.isPresent) {
            sessionToReturn.get().let { session ->

                val tabletopDTO = tabletopService.retrieveTabletopById(session.tabletop!!.id!!)
                val ordersDTO = getOrdersAsDTO(session.orders)
                val paymentsDTO = getPaymentsAsDTO(session.payments)

                session.convertToSessionDTO(tabletopDTO, ordersDTO, paymentsDTO)
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }

    fun retrieveAllSessions(): ArrayList<SessionDTO> {

        return sessionRepository.findAll()
            .map { session ->

                val tabletopDTO = tabletopService.retrieveTabletopById(session.tabletop!!.id!!)
                val ordersDTO = getOrdersAsDTO(session.orders)
                val paymentsDTO = getPaymentsAsDTO(session.payments)

                session.convertToSessionDTO(tabletopDTO, ordersDTO, paymentsDTO)

            } as ArrayList<SessionDTO>
    }

    fun updateSession(id: Int, sessionDTO: SessionDTO): SessionDTO {

        val sessionToUpdate = sessionRepository.findById(id)
        val tabletop = sessionDTO.tabletop?.authCode?.let { authCode ->
            Tabletop(sessionDTO.tabletop?.id, authCode)
        }

        return if (sessionToUpdate.isPresent) {
            sessionToUpdate.get().let { session ->

                session.status = sessionDTO.status.toString()
                session.tabletop = tabletop
                session.total = sessionDTO.total

                if (sessionDTO.orders != null) {
                    for (o in sessionDTO.orders!!) {
                        orderService.updateOrder(o.id!!, o)
                    }
                }

                if (sessionDTO.payments != null) {
                    for (p in sessionDTO.payments!!) {
                        molliePaymentService.updateMolliePayment(p.molliePaymentId!!, p)
                    }
                }

                sessionRepository.save(session)

                val tabletopDTO = tabletopService.retrieveTabletopById(session.tabletop!!.id!!)
                val ordersDTO = getOrdersAsDTO(session.orders)
                val paymentsDTO = getPaymentsAsDTO(session.payments)

                session.convertToSessionDTO(tabletopDTO, ordersDTO, paymentsDTO)
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

            val sessionByTabletopId = sessionRepository.findByTabletopId(tabletopId, "CLOSED")

            return if (sessionByTabletopId != null) {

                logger.info("Session found: $sessionByTabletopId")
                sessionByTabletopId.id?.let { retrieveSessionById(it) }

            } else {

                val tabletopDTO = TabletopDTO(tabletopId, authCode)
                val sessionDTO = SessionDTO(id = null, tabletop = tabletopDTO)

                createSession(sessionDTO)
            }
        } else {
            return null
        }
    }

    fun updateSessionStatus(id: String) {

        logger.info("Webhook call received for Mollie payment id: $id")

        val molliePaymentDTO = molliePaymentService.retrieveMolliePaymentByMolliePaymentId(id)
        val session = molliePaymentDTO.sessionId?.let { sessionRepository.findById(it) }

        if (session != null) {
            if (session.isPresent) {
                session.get().let {
                    it.status = "CHANGED"
                    sessionRepository.save(it)
                    logger.info("Session updated for session with id: ${it.id}")
                }
            }
        }
    }

    private fun getOrdersAsDTO(orders: MutableList<Order>): ArrayList<OrderDTO> {

        val ordersDTO = ArrayList<OrderDTO>().apply {
            for (o in orders) {
                this.add(orderService.retrieveOrderById(o.id!!))
            }
        }

        return ordersDTO
    }

    private fun getPaymentsAsDTO(payments: MutableList<MolliePayment>): ArrayList<MolliePaymentDTO> {

        val paymentsDTO = ArrayList<MolliePaymentDTO>().apply {
            for (p in payments) {
                this.add(molliePaymentService.retrieveMolliePaymentById(p.molliePaymentId!!))
            }
        }

        return paymentsDTO
    }
}

