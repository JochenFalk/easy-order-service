package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotBlank

data class TabletopDTO(
    var id: Int? = 0,
    @get:NotBlank(message = "Property 'authCode' cannot be blank")
    var authCode: String,
)

