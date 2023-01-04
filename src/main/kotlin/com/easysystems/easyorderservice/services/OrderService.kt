package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.exceptions.OrderNotFoundException
import com.easysystems.easyorderservice.exceptions.SessionNotValidException
import com.easysystems.easyorderservice.repositories.OrderRepository
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val sessionRepository: SessionRepository,
    val itemService: ItemService
) {

    companion object : KLogging()

    fun createOrder(orderDTO: OrderDTO): OrderDTO {

        // Using repo instead of service to circumvent circular reference warning between session-
        // and order-service
        val sessionOptional = sessionRepository.findById(orderDTO.sessionId!!)

        if (!sessionOptional.isPresent) {
            throw SessionNotValidException("Session is not valid for given id: ${orderDTO.sessionId}")
        }

        val order = orderDTO.convertToOrder(id = null, session = sessionOptional.get())

        orderRepository.save(order)
        logger.info("New order created: $order")

        return order.let {
            it.convertToOrderDTO(getItemsAsDTO(it.items!!))
        }
    }

    fun retrieveOrderById(id: Int): OrderDTO {

        val order = orderRepository.findById(id)

        return if (order.isPresent) {
            order.get().let {
                it.convertToOrderDTO(getItemsAsDTO(it.items!!))
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }

    fun retrieveAllOrders(): ArrayList<OrderDTO> {

        return orderRepository.findAll()
            .map {
                it.convertToOrderDTO(getItemsAsDTO(it.items!!))
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
                it.convertToOrderDTO(orderDTO.items!!)
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

    private fun getItemsAsDTO(items: ArrayList<Int>) : ArrayList<ItemDTO> {

        val itemsAsDTO = ArrayList<ItemDTO>().apply {
            for (i in items) {
                this.add(itemService.retrieveItemById(i))
            }
        }

        return itemsAsDTO
    }
}