package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotBlank

data class CustomerDTO(
    override var id: Int? = 0,
    @get:NotBlank(message = "Property 'name' cannot be blank")
    override var name: String
) : UserDTO(id, name)