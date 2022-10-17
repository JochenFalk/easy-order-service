package com.easysystems.easyorderservice.entities

import javax.persistence.*

@Entity
@Table(name = "Employees")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override val id: Int?,
    override var name: String,
    var password: String
) : User(id, name)