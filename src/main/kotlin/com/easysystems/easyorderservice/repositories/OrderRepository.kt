package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Order
import org.springframework.data.repository.CrudRepository

interface OrderRepository : CrudRepository<Order, Int>
