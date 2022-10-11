package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.data.Customer
import com.easysystems.easyorderservice.data.UserDTO
import com.easysystems.easyorderservice.interfaces.CRUDRepository

var mainUserListDTO: ArrayList<UserDTO> = ArrayList()

class UserRepository : CRUDRepository {

    override fun getById(id: Int): UserDTO? {

        for (i in mainUserListDTO) {
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

        return customer.itemDTOs
            .map { it.price }
            .sum()
    }
}