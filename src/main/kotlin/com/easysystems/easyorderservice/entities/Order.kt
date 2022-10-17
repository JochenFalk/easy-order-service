package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID", nullable = false)
    var item: Item? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TABLETOP_ID", nullable = false)
    var tabletop: Tabletop? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    var session: Session? = null
) {
    override fun toString(): String {
        return "Order(id:$id, itemId:${item!!.id}, tabletop: ${tabletop!!.id} session:${session!!.id})"
    }
}