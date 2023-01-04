package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.exceptions.ItemNotFoundException
import com.easysystems.easyorderservice.repositories.ItemRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class ItemService(val itemRepository: ItemRepository) {

    companion object : KLogging()

    fun createItem(itemDTO: ItemDTO): ItemDTO {

        val item = itemDTO.convertToItem(id = null)

        itemRepository.save(item)
        logger.info("New item created: $item")

        return item.convertToItemDTO()
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

        return if (item.isPresent) {
            item.get().convertToItemDTO()
        } else {
            throw ItemNotFoundException("No item found for given id: $id")
        }
    }

    fun retrieveAllItems(categoryFilter: String?): ArrayList<ItemDTO> {

        val result = categoryFilter?.let {
            itemRepository.findByCategory(categoryFilter)
        } ?: itemRepository.findAll()

        return result
            .map {
                it.convertToItemDTO()
            } as ArrayList<ItemDTO>
    }

    fun updateItem(id: Int, itemDTO: ItemDTO): ItemDTO {

        val item = itemRepository.findById(id)

        return if (item.isPresent) {
            item.get().let {

                it.name = itemDTO.name
                it.category = itemDTO.category.toString()
                it.price = itemDTO.price

                itemRepository.save(it)
                it.convertToItemDTO()
            }
        } else {
            throw ItemNotFoundException("No item found for given id: $id")
        }
    }

    fun deleteItem(id: Int) {

        val item = itemRepository.findById(id)

        if (item.isPresent) {
            item.get().let {
                itemRepository.deleteById(id)
            }
        } else {
            throw ItemNotFoundException("No item found for given id: $id")
        }
    }
}