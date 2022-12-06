package com.easysystems.easyorderservice.data

import javax.validation.constraints.NotNull

data class MolliePaymentDTO(
    val molliePaymentId: Int? = 0,
    val amount: HashMap<String, String>? = HashMap(),
    val createdAt: String? = null,
    val description: String? = null,
    val expiresAt: String? = null,
    val id: String? = null,
    val isCancelable: Boolean? = null,
    val mode: String? = null,
    val profileId: String? = null,
    val checkoutUrl: String? = null,
    val redirectUrl: String? = null,
    val webhookUrl: String? = null,
    val resource: String? = null,
    val sequenceType: String? = null,
    val status: String? = null,
    @get:NotNull(message = "Property 'sessionId' cannot be null")
    val sessionId: Int? = null
)