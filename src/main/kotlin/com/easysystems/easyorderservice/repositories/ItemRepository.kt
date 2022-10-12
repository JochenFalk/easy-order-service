package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepository : CrudRepository<Item, Int> {

}