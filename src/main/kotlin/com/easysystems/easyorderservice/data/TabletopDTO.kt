package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.repositories.mainTabletopListDTO

data class TabletopDTO(
    var id: Int?=0,
    var code: String,
    var customers: ArrayList<Customer> = ArrayList(),
    var itemsDTO: ArrayList<ItemDTO> = ArrayList()
) {

    init {
        id = mainTabletopListDTO.size + 1
        mainTabletopListDTO.add(this)
    }

    fun addItemToTabletop(item: ItemDTO, customer: Customer)
    {
        customer.itemDTOs.add(item)
        itemsDTO.add(item)
    }
}