package com.easysystems.easyorderservice.entities;

import com.easysystems.easyorderservice.data.Customer
import javax.persistence.*

@Entity
@Table(name = "Tabletops")
data class Tabletop (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,
    var code: String,
    var customers: ArrayList<Customer> = ArrayList(),
    var items: ArrayList<Item> = ArrayList()
)