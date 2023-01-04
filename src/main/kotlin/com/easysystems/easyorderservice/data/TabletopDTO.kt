package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.entities.Tabletop
import javax.validation.constraints.NotBlank

data class TabletopDTO(
    var id: Int?,
    @get:NotBlank(message = "Property 'authCode' cannot be blank")
    var authCode: String
) {

    fun convertToTabletop(id: Int?): Tabletop {
        return Tabletop(
            id = id ?: this.id,
            authCode = this.authCode
        )
    }
}

