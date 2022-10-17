package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotNull

class OrderDTO (
    var id: Int? = 0,
    @get:NotNull(message = "Property 'itemId' cannot be null")
    var itemId: Int? = null,
    @get:NotNull(message = "Property 'tableId' cannot be null")
    var tabletopId: Int? = null,
    @get:NotNull(message = "Property 'sessionId' cannot be null")
    val sessionId: Int? = null
)