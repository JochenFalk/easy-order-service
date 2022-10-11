package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int?,
    var name: String
)