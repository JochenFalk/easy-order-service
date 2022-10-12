package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Users")
open class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open val id: Int?,
    open var name: String
)