package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.exceptions.*
import com.easysystems.easyorderservice.repositories.OrderRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository,
                   val itemService: ItemService,
                   val tabletopService: TabletopService,
                   val sessionService: SessionService) {

    companion object : KLogging()

    fun createOrder(orderDTO: OrderDTO): OrderDTO {

        val item = itemService.retrieveOptionalItemById(orderDTO.itemId!!)
        val tabletop = tabletopService.retrieveOptionalTabletopById(orderDTO.tabletopId!!)
        val session = sessionService.retrieveOptionalSessionById(orderDTO.sessionId!!)

        if (!item.isPresent)
        {
            throw ItemNotValidException("Item not valid for given id: ${orderDTO.itemId}")
        }

        if (!tabletop.isPresent)
        {
            throw TabletopNotValidException("Table not valid for given id: ${orderDTO.tabletopId}")
        }

        if (!session.isPresent)
        {
            throw SessionNotValidException("Session not valid for given id: ${orderDTO.sessionId}")
        }

        val order = orderDTO.let {
            Order(null, item.get(), tabletop.get(), session.get())
        }

        orderRepository.save(order)

        logger.info("New order created: $order")

        return order.let {
            OrderDTO(it.id, it.item!!.id, it.tabletop!!.id, it.session!!.id)
        }
    }

    fun retrieveOrderById(id: Int): OrderDTO {

        val order = orderRepository.findById(id)

        return if (order.isPresent)
        {
            order.get().let {
                OrderDTO(it.id, it.item!!.id, it.tabletop!!.id, it.session!!.id)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }

    fun retrieveAllOrders(): ArrayList<OrderDTO> {

        return orderRepository.findAll()
            .map {
                OrderDTO(it.id, it.item!!.id, it.tabletop!!.id, it.session!!.id)
            } as ArrayList<OrderDTO>
    }

    fun updateOrder(id: Int, orderDTO: OrderDTO): OrderDTO {

        val order = orderRepository.findById(id)

        return if(order.isPresent)
        {
            val item = itemService.retrieveOptionalItemById(orderDTO.itemId!!)
            val tabletop = tabletopService.retrieveOptionalTabletopById(orderDTO.tabletopId!!)
            val session = sessionService.retrieveOptionalSessionById(orderDTO.sessionId!!)

            if (!item.isPresent)
            {
                throw ItemNotValidException("Item not valid for given id: ${orderDTO.itemId}")
            }

            if (!tabletop.isPresent)
            {
                throw TabletopNotValidException("Table not valid for given id: ${orderDTO.tabletopId}")
            }

            if (!session.isPresent)
            {
                throw SessionNotValidException("Session not valid for given id: ${orderDTO.sessionId}")
            }

            order.get().let {
                it.item = item.get()
                it.tabletop = tabletop.get()
                it.session = session.get()
                orderRepository.save(it)

                OrderDTO(it.id, item.get().id, tabletop.get().id, session.get().id)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }

    fun deleteOrder(id: Int) {

        val order = orderRepository.findById(id)

        if(order.isPresent)
        {
            order.get().let {
                orderRepository.deleteById(id)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }
}