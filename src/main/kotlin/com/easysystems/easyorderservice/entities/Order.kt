package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Orders")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var itemId: Int,
    var customerId: Int
)
