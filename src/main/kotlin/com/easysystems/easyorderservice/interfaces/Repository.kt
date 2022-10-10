package com.easysystems.easyorderservice.interfaces

interface Repository {

    fun getById(id: Int): Any?
    fun saveById(id: Int): Boolean
}