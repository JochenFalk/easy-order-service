package com.easysystems.easyorderservice.entities;

import com.easysystems.easyorderservice.data.TabletopDTO
import javax.persistence.*

@Entity
@Table(name = "Tabletops")
data class Tabletop (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var authCode: String
) {

    fun convertToTabletopDTO(): TabletopDTO {
        return TabletopDTO(
            id = this.id,
            authCode = this.authCode
        )
    }
}