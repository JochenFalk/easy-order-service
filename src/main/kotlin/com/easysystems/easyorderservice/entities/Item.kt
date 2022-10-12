package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Items")
data class Item (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,
    var price: Float
)