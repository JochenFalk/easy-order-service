package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.entities.Session
import com.easysystems.easyorderservice.entities.Tabletop
import com.easysystems.easyorderservice.exceptions.SessionNotFoundException
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class SessionService(val sessionRepository: SessionRepository,
                     val tabletopService: TabletopService,
                     val orderService: OrderService,
                     val authenticationService: AuthenticationService) {

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

                for (o in it.orders!!) {
                    orderService.retrieveOrderById(o.id!!).let { orderDTO ->
                        ordersDTO.add(orderDTO)
                    }
                }

                SessionDTO(it.id, SessionDTO.Status.valueOf(it.status!!), tabletopDTO, it.total, ordersDTO)
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

                for (o in it.orders!!) {
                    orderService.retrieveOrderById(o.id!!).let { orderDTO ->
                        ordersDTO.add(orderDTO)
                    }
                }

                SessionDTO(it.id, SessionDTO.Status.valueOf(it.status!!), tabletopDTO, it.total, ordersDTO)

            } as ArrayList<SessionDTO>
    }

    fun updateSession(id: Int, sessionDTO: SessionDTO): SessionDTO {

        val session = sessionRepository.findById(id)

        return if (session.isPresent) {
            session.get().let {

                val tabletop = Tabletop(sessionDTO.tabletopDTO!!.id, sessionDTO.tabletopDTO!!.authCode)
                val orders = ArrayList<Order>()

                for (o in it.orders!!) {
                    val order = Order(o.id, o.status, o.items, o.total, o.session)
                    orders.add(order)
                }

                it.status = sessionDTO.status.toString()
                it.tabletop = tabletop
                it.total = sessionDTO.total
                it.orders = orders

                sessionRepository.save(it)

                val tabletopDTO = tabletopService.retrieveTabletopById(it.tabletop!!.id!!)
                val ordersDTO = ArrayList<OrderDTO>()

                for (o in it.orders!!) {
                    orderService.retrieveOrderById(o.id!!).let { orderDTO ->
                        ordersDTO.add(orderDTO)
                    }
                }

                SessionDTO(it.id, SessionDTO.Status.valueOf(it.status!!), tabletopDTO, it.total, ordersDTO)
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

            val sessionByTabletopId = sessionRepository.findByTabletopId(tabletopId)

            return if (sessionByTabletopId != null) {

                logger.info("Session found: $sessionByTabletopId")

                sessionByTabletopId.id?.let { retrieveSessionById(it) }
            } else {

                val tabletopDTO = TabletopDTO(tabletopId, authCode)
                val sessionDTO = SessionDTO(tabletopDTO=tabletopDTO)

                createSession(sessionDTO)
            }
        } else {
            return null
        }
    }
}
