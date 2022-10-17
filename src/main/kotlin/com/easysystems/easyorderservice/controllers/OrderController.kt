package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.services.OrderService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/orders")
@Validated
class OrderController(val orderService: OrderService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody @Valid orderDTO: OrderDTO): OrderDTO {
        return orderService.createOrder(orderDTO)
    }

    @GetMapping("/{id}")
    fun retrieveOrderById(@PathVariable("id") id: Int): OrderDTO {
        return orderService.retrieveOrderById(id)
    }

    @GetMapping
    fun retrieveAllOrders(): ArrayList<OrderDTO> {
        return orderService.retrieveAllOrders()
    }

    @PutMapping("/{id}")
    fun updateOrder(@RequestBody @Valid orderDTO: OrderDTO, @PathVariable("id") id: Int): OrderDTO {
        return orderService.updateOrder(id, orderDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@PathVariable("id") id: Int) {
        return orderService.deleteOrder(id)
    }
}