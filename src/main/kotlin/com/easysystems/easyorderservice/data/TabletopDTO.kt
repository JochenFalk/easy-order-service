package com.easysystems.easyorderservice.data


data class TabletopDTO(
    var id: Int?=0,
    var code: String,
    var customersDTO: ArrayList<CustomerDTO> = ArrayList(),
    var itemsDTO: ArrayList<ItemDTO> = ArrayList()
) {

    fun addItemToTabletop(item: ItemDTO, customerDTO: CustomerDTO)
    {
        customerDTO.items.add(item)
        itemsDTO.add(item)
    }
}