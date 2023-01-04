package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.services.ItemService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/items")
@Validated
class ItemController(val itemService: ItemService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createItemsByList(@RequestBody @Valid itemList: ArrayList<ItemDTO>): ArrayList<ItemDTO> {
        return itemService.createItemsByList(itemList)
    }

    @GetMapping("/{id}")
    fun retrieveItemById(@PathVariable("id") id: Int): ItemDTO {
        return itemService.retrieveItemById(id)
    }

    @GetMapping
    fun retrieveAllItems(@RequestParam("category_filter", required = false) categoryFilter: String?): ArrayList<ItemDTO> {
        return itemService.retrieveAllItems(categoryFilter?.uppercase())
    }

    @PutMapping("/{id}")
    fun updateItem(@RequestBody @Valid itemDTO: ItemDTO, @PathVariable("id") id: Int): ItemDTO {
        return itemService.updateItem(id, itemDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteItem(@PathVariable("id") id: Int) {
        return itemService.deleteItem(id)
    }
}