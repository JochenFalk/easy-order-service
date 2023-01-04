package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.entities.MolliePayment
import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.entities.Session
import com.easysystems.easyorderservice.entities.Tabletop
import javax.validation.constraints.NotNull

data class SessionDTO(
    var id: Int?,
    var status: Status? = Status.OPENED,
    @get:NotNull(message = "Property 'tabletopDTO' cannot be null")
    var tabletop: TabletopDTO? = null,
    @get:NotNull(message = "Property 'total' cannot be null")
    var total: Double? = 0.0,
    var orders: ArrayList<OrderDTO>? = ArrayList(),
    var payments: ArrayList<MolliePaymentDTO>? = ArrayList()
) {
    enum class Status {
        OPENED,
        CLOSED,
        LOCKED,
        CHANGED
    }

    fun convertToSession(
        id: Int?,
        tabletop: Tabletop,
        orders: ArrayList<Order>,
        payments: ArrayList<MolliePayment>
    ): Session {

        return Session(
            id = id ?: this.id,
            status = this.status.toString(),
            tabletop = tabletop,
            total = this.total,
            orders = orders,
            payments = payments
        )
    }
}