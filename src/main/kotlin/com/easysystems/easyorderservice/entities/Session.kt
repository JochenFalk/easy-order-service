package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Sessions")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var status: String,
    @OneToMany(
        mappedBy = "session",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var orders: List<Order> = mutableListOf()
)
