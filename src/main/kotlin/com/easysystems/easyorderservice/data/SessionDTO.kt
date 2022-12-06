package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotNull

data class SessionDTO(
    var id: Int? = 0,
    var status: Status? = Status.OPENED,
    @get:NotNull(message = "Property 'tabletopDTO' cannot be null")
    var tabletopDTO: TabletopDTO? = null,
    @get:NotNull(message = "Property 'total' cannot be null")
    var total: Double? = 0.0,
    var orders: ArrayList<OrderDTO>? = ArrayList(),
    var payment: MolliePaymentDTO? = null
) {
    enum class Status {
        OPENED,
        CLOSED,
        LOCKED
    }
}