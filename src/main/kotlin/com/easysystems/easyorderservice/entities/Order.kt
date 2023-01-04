package com.easysystems.easyorderservice.entities

import com.easysystems.easyorderservice.data.ItemDTO
import com.easysystems.easyorderservice.data.OrderDTO
import javax.persistence.*

@Entity
@Table(name = "Orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var status: String? = "",
    var items: ArrayList<Int>? = ArrayList(),
    var total: Double? = 0.0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    val session: Session? = null
) {
    override fun toString(): String {
        return "Order(id:$id, status:$status, items: $items, total: $total, session: ${session?.id})"
    }

    fun convertToOrderDTO(itemsAsDTO: ArrayList<ItemDTO>): OrderDTO {

        val status = when (this.status) {
            "OPENED" -> OrderDTO.Status.OPENED
            "SENT" -> OrderDTO.Status.SENT
            "CANCELED" -> OrderDTO.Status.CANCELED
            "LOCKED" -> OrderDTO.Status.LOCKED
            else -> { "" }
        }

        return OrderDTO(
            id = this.id,
            status = status as OrderDTO.Status,
            items = itemsAsDTO,
            total = this.total,
            sessionId = this.session?.id
        )
    }
}