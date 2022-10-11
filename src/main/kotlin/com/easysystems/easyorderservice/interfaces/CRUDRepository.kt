package com.easysystems.easyorderservice.interfaces

interface CRUDRepository {

    fun getById(id: Int): Any?
    fun saveById(id: Int): Boolean
}