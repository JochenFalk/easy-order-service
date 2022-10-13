package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Customers")
data class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override val id: Int?,
    override var name: String,
    var tableId: Int=0,
    var items: ArrayList<Int> = ArrayList()
) : User(id, name)