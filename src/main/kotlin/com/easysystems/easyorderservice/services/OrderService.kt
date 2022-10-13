package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.exceptions.ItemNotFoundException
import com.easysystems.easyorderservice.exceptions.OrderNotFoundException
import com.easysystems.easyorderservice.repositories.OrderRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository) {

    companion object : KLogging()

    fun createOrder(orderDTO: OrderDTO): OrderDTO {

        val order = orderDTO.let {
            Order(null, it.itemId, it.customerId)
        }

        orderRepository.save(order)

        logger.info("New order created: $order")

        return order.let {
            OrderDTO(it.id, it.itemId, it.customerId)
        }
    }

    fun retrieveOrderById(id: Int): OrderDTO {

        val order = orderRepository.findById(id)

        return if (order.isPresent)
        {
            order.get().let {
                OrderDTO(it.id, it.itemId, it.customerId)
            }
        } else {
            throw OrderNotFoundException("No order found for given id: $id")
        }
    }

    fun retrieveAllOrders(): ArrayList<OrderDTO> {

        return orderRepository.findAll()
            .map {
                OrderDTO(it.id, it.itemId, it.customerId)
            } as ArrayList<OrderDTO>
    }

    fun updateOrder(id: Int, orderDTO: OrderDTO): OrderDTO {

        val order = orderRepository.findById(id)

        return if(order.isPresent)
        {
            order.get().let {
                it.itemId = orderDTO.itemId
                it.customerId = orderDTO.customerId
                orderRepository.save(it)

                OrderDTO(it.id, it.itemId, it.customerId)
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
