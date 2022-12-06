package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Sessions")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var status: String? = "",
    @OneToOne
    @JoinColumn(name = "TABLETOP_ID")
    var tabletop: Tabletop? = null,
    var total: Double? = 0.0,
    @OneToMany(
        mappedBy = "session",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var orders: MutableList<Order> = mutableListOf(),
    @OneToOne(
        mappedBy = "session",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var payment: MolliePayment? = null
)
