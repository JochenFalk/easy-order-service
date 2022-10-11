package com.easysystems.easyorderservice.entities

import com.easysystems.easyorderservice.data.ItemDTO
import javax.persistence.*

@Entity
@Table(name = "Items")
data class Item (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,
    var name: String,
    var category: ItemDTO.Category,
    var price: Float
)