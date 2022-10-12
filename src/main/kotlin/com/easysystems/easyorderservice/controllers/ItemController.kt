package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.services.ItemService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/items")
class ItemController(val itemService: ItemService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createItem(@RequestBody itemDTO: ItemDTO): ItemDTO {
        return itemService.createItem(itemDTO)
    }

    @GetMapping("/{id}")
    fun retrieveItemById(@PathVariable("id") id: Int): ItemDTO {
        return itemService.retrieveItemById(id)
    }

    @GetMapping
    fun retrieveAllItems(): ArrayList<ItemDTO> {
        return itemService.retrieveAllItems()
    }

    @PutMapping("/{id}")
    fun updateItem(@RequestBody itemDTO: ItemDTO, @PathVariable("id") id: Int): ItemDTO {
        return itemService.updateItem(id, itemDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteItem(@PathVariable("id") id: Int) {
        return itemService.deleteItem(id)
    }
}