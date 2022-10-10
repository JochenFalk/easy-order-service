package com.easysystems.easyorderservice.data

data class Table(
    var id: Int = 0,
    var code: String,
    var customerList: ArrayList<Customer> = ArrayList(),
    var items: ArrayList<Item> = ArrayList()
) {

    init {
        id = mainTableList.size + 1
        mainTableList.add(this)
    }

    fun addItemToTable(item: Item, customer: Customer)
    {
        customer.items.add(item)
        items.add(item)
    }
}