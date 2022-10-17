package com.easysystems.easyorderservice.entities;

import javax.persistence.*

@Entity
@Table(name = "Tabletops")
data class Tabletop (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var authCode: String,
)