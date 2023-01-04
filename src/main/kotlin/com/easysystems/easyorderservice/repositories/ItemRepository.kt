package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Item
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Int> {

    fun findByCategory(category: String): ArrayList<Item>
}