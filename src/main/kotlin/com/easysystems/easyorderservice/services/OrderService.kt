package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.exceptions.*
import com.easysystems.easyorderservice.repositories.OrderRepository
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
import org.springframework.stereotype.Service
import kotlin.collections.ArrayList

@Service
class OrderService(val orderRepository: OrderRepository,
                   val sessionRepository: SessionRepository,
                   val itemService: ItemService) {

    companion object : KLogging()

    fun createOrder(orderDTO: OrderDTO): OrderDTO {

        // Using repo instead of service to circumvent circular reference warning between session-
        // and order-service
        val sessionOptional = sessionRepository.findById(orderDTO.sessionId!!)

        if(!sessionOptional.isPresent){
            throw SessionNotValidException("Session is not valid for given id: ${orderDTO.sessionId}")
        }

        val order = orderDTO.let {

            val itemsAsListOfId = ArrayList<Int>()

            for (i in it.items!!) {
                itemsAsListOfId.add(i.id!!)
            }

            Order(null, it.status.toString(), itemsAsListOfId, it.total!!, sessionOptional.get())
        }

        orderRepository.save(order)

        logger.info("New order created: $order")

        return order.let {

            val itemsAsDTO = ArrayList<ItemDTO>()

            for (i in it.items!!) {
                itemsAsDTO.add(itemService.retrieveItemById(i))
            }

            OrderDTO(it.id, OrderDTO.Status.valueOf(it.status!!), itemsAsDTO, it.total, it.session!!.id)
        }
    }

    fun retrieveOrderById(id: Int): OrderDTO {

        val order = orderRepository.findById(id)

        return if (order.isPresent) {
            order.get().let {

                val itemsAsDTO = ArrayList<ItemDTO>()

                for (i in it.items!!) {
                    itemsAsDTO.add(itemService.retrieveItemById(i))
                }

                OrderDTO(it.id, OrderDTO.Status.valueOf(it.status!!), itemsAsDTO, it.total, it.session!!.id)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }

    fun retrieveAllOrders(): ArrayList<OrderDTO> {

        return orderRepository.findAll()
            .map {

                val itemsAsDTO = ArrayList<ItemDTO>()

                for (i in it.items!!) {
                    itemsAsDTO.add(itemService.retrieveItemById(i))
                }

                OrderDTO(it.id, OrderDTO.Status.valueOf(it.status!!), itemsAsDTO, it.total, it.session!!.id)

            } as ArrayList<OrderDTO>
    }

    fun updateOrder(id: Int, orderDTO: OrderDTO): OrderDTO {

        val order = orderRepository.findById(id)

        return if (order.isPresent) {
            order.get().let {

                val itemsAsListOfId = ArrayList<Int>()

                for (i in orderDTO.items!!) {
                    itemsAsListOfId.add(i.id!!)
                }

                it.status = orderDTO.status.toString()
                it.items = itemsAsListOfId
                it.total = orderDTO.total

                orderRepository.save(it)

                OrderDTO(it.id, OrderDTO.Status.valueOf(it.status!!), orderDTO.items, it.total, it.session!!.id)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }

    fun deleteOrder(id: Int) {

        val order = orderRepository.findById(id)

        if (order.isPresent) {
            order.get().let {
                orderRepository.deleteById(id)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }
}