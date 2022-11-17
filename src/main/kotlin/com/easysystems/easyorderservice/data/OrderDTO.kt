package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotNull

class OrderDTO (
    var id: Int? = 0,
    var status: Status? = Status.OPENED,
    var items: ArrayList<ItemDTO>? = ArrayList(),
    @get:NotNull(message = "Property 'total' cannot be null")
    var total: Double? = 0.0,
    @get:NotNull(message = "Property 'sessionId' cannot be null")
    val sessionId : Int? = null
) {
    enum class Status {
        OPENED,
        SENT
    }
}