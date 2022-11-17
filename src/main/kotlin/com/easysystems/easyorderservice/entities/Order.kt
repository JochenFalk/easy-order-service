package com.easysystems.easyorderservice.entities

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
}