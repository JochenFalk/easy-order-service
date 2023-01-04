package com.easysystems.easyorderservice.entities

import com.easysystems.easyorderservice.data.ItemDTO
import javax.persistence.*

@Entity
@Table(name = "Items")
data class Item(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var name: String,
    var category: String,
    var price: Double
) {
    fun convertToItemDTO(): ItemDTO {

        val category = when (this.category) {
            "APPETIZER" -> ItemDTO.Category.APPETIZER
            "MAIN" -> ItemDTO.Category.MAIN
            "DESERT" -> ItemDTO.Category.DESERT
            "DRINKS" -> ItemDTO.Category.DRINKS
            else -> { "" }
        }

        return ItemDTO(
            id = this.id,
            name = this.name,
            category = category as ItemDTO.Category,
            price = this.price
        )
    }
}