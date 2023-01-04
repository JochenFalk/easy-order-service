package com.easysystems.easyorderservice.data

import com.easysystems.easyorderservice.entities.MolliePayment
import com.easysystems.easyorderservice.entities.Session
import javax.validation.constraints.NotNull

data class MolliePaymentDTO(
    val molliePaymentId: Int?,
    val amount: HashMap<String, String>? = HashMap(),
    val createdAt: String? = null,
    val description: String? = null,
    val expiresAt: String? = null,
    val id: String? = null,
    val isCancelable: Boolean? = null,
    val method: String? = null,
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
) {
     fun convertToMolliePayment(session: Session): MolliePayment {
         return MolliePayment(
             molliePaymentId = this.molliePaymentId,
             amount = this.amount,
             createdAt = this.createdAt,
             description = this.description,
             expiresAt = this.expiresAt,
             id = this.id,
             isCancelable = this.isCancelable,
             method = this.method,
             mode = this.mode,
             profileId = this.profileId,
             checkoutUrl = this.checkoutUrl,
             redirectUrl = this.redirectUrl,
             webhookUrl = this.webhookUrl,
             resource = this.resource,
             sequenceType = this.sequenceType,
             status = this.status,
             session = session
         )
     }
}