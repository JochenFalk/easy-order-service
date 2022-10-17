package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.entities.Item
import com.easysystems.easyorderservice.exceptions.ItemNotFoundException
import com.easysystems.easyorderservice.repositories.ItemRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class ItemService(val itemRepository: ItemRepository) {

    companion object : KLogging()

    fun createItem(itemDTO: ItemDTO): ItemDTO {

        val item = itemDTO.let {
            Item(null, it.name, it.category.toString(), it.price)
        }

        itemRepository.save(item)

        logger.info("New item created: $item")

        return item.let {
            val category: ItemDTO.Category = ItemDTO.Category.valueOf(it.category)
            ItemDTO(it.id, it.name, category, it.price)
        }
    }

    fun createItemsByList(itemList: ArrayList<ItemDTO>): ArrayList<ItemDTO> {

        val createdItems = ArrayList<ItemDTO>()

        itemList.forEach { outer ->
            createItem(outer).let { inner ->
                createdItems.add(inner)
            }
        }

        return createdItems
    }

    fun retrieveItemById(id: Int): ItemDTO {

        val item = itemRepository.findById(id)

        return if (item.isPresent)
        {
            item.get().let {
                val category: ItemDTO.Category = ItemDTO.Category.valueOf(it.category)
                ItemDTO(it.id, it.name, category, it.price)
            }
        } else {
            throw ItemNotFoundException("No item found for given id: $id")
        }
    }

    fun retrieveOptionalItemById(id: Int): Optional<Item> {
        return itemRepository.findById(id)
    }

    fun retrieveAllItems(categoryFilter: String?): ArrayList<ItemDTO> {

        val result = categoryFilter?.let {
            itemRepository.findByCategory(categoryFilter)
        } ?: itemRepository.findAll()

        return result
            .map {
                val category = ItemDTO.Category.valueOf(it.category)
                ItemDTO(it.id, it.name, category, it.price)
            } as ArrayList<ItemDTO>
    }

    fun updateItem(id: Int, itemDTO: ItemDTO): ItemDTO {

        val item = itemRepository.findById(id)

        return if(item.isPresent)
        {
            item.get().let {
                it.name = itemDTO.name
                it.category = itemDTO.category.toString()
                it.price = itemDTO.price
                itemRepository.save(it)

                val category = ItemDTO.Category.valueOf(it.category)
                ItemDTO(it.id, it.name, category, it.price)
            }
        } else {
            throw ItemNotFoundException("No item found for given id: $id")
        }
    }

    fun deleteItem(id: Int) {

        val item = itemRepository.findById(id)

        if(item.isPresent)
        {
            item.get().let {
                itemRepository.deleteById(id)
            }
        } else {
            throw ItemNotFoundException("No item found for given id: $id")
        }
    }
}