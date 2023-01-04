package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.entities.Order
import com.easysystems.easyorderservice.entities.Session
import javax.validation.constraints.NotNull

class OrderDTO(
    var id: Int?,
    var status: Status? = Status.OPENED,
    var items: ArrayList<ItemDTO>? = ArrayList(),
    @get:NotNull(message = "Property 'total' cannot be null")
    var total: Double? = 0.0,
    @get:NotNull(message = "Property 'sessionId' cannot be null")
    val sessionId: Int? = null
) {
    enum class Status {
        OPENED,
        SENT,
        CANCELED,
        LOCKED
    }

    fun convertToOrder(id: Int?, session: Session): Order {

        val itemsAsListOfId = ArrayList<Int>()

        for (i in this.items!!) {
            i.id?.let { itemsAsListOfId.add(it) }
        }

        return Order(
            id = id ?: this.id,
            status = this.status.toString(),
            items = itemsAsListOfId,
            total = this.total,
            session = session
        )
    }
}