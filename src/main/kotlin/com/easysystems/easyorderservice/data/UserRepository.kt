package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.interfaces.Repository

var mainUserList: ArrayList<User> = ArrayList()

class UserRepository : Repository {

    override fun getById(id: Int): User? {

        for (i in mainUserList) {
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

    fun getTotalFromCustomer(customer: Customer): Float {

        return customer.items
            .map { it.price }
            .sum()
    }
}