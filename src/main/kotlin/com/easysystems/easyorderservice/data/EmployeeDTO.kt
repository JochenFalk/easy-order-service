package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotBlank

data class EmployeeDTO(
    override var id: Int? = 0,
    @get:NotBlank(message = "Property 'name' cannot be blank")
    override var name: String,
    @get:NotBlank(message = "Property 'password' cannot be blank")
    var password: String
) : UserDTO(id, name)