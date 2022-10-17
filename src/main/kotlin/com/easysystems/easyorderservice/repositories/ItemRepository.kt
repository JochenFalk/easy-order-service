package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Item
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Int> {

    fun findByCategory(category: String): ArrayList<Item>
    fun findByNameContainingIgnoreCase(name: String): ArrayList<Item>
    @Query(value = "SELECT * FROM ITEMS WHERE price BETWEEN ?1 and ?2", nativeQuery = true)
    fun findByPrice(price1: Float, price2: Float): ArrayList<Item>
}