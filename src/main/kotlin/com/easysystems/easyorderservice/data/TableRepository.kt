package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.interfaces.Repository

var mainTableList: ArrayList<Table> = ArrayList()

class TableRepository : Repository {

    init {
        for (i in 1..5) {
            Table(code = "code$i")
        }
    }

    override fun getById(id: Int): Table? {

        for (i in mainTableList) {
            if (i.id == id) {
                return i
            }
        }

        return null
    }

    override fun saveById(id: Int): Boolean {
        //
        return true
    }

    fun getTotal(table: Table): Float {
        return table.items
            .map { it.price }
            .sum()
    }
}