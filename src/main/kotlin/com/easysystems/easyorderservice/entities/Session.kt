package com.easysystems.easyorderservice.entities

import com.easysystems.easyorderservice.data.MolliePaymentDTO
import com.easysystems.easyorderservice.data.OrderDTO
import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.data.TabletopDTO
import javax.persistence.*

@Entity
@Table(name = "Sessions")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int?,
    var status: String? = "",
    @OneToOne
    @JoinColumn(name = "TABLETOP_ID")
    var tabletop: Tabletop? = null,
    var total: Double? = 0.0,
    @OneToMany(
        mappedBy = "session",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var orders: MutableList<Order> = mutableListOf(),
    @OneToMany(
        mappedBy = "session",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var payments: MutableList<MolliePayment> = mutableListOf()
) {

    fun convertToSessionDTO(
        tabletopDTO: TabletopDTO,
        ordersDTO: ArrayList<OrderDTO>,
        paymentsDTO: ArrayList<MolliePaymentDTO>
    ): SessionDTO {

        val status = when (this.status) {
            "OPENED" -> SessionDTO.Status.OPENED
            "CLOSED" -> SessionDTO.Status.CLOSED
            "LOCKED" -> SessionDTO.Status.LOCKED
            "CHANGED" -> SessionDTO.Status.CHANGED
            else -> { "" }
        }

        return SessionDTO(
            id = this.id,
            status = status as SessionDTO.Status,
            tabletop = tabletopDTO,
            total = this.total,
            orders = ordersDTO,
            payments = paymentsDTO
        )
    }
}
