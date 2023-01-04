package com.easysystems.easyorderservice.entities

import com.easysystems.easyorderservice.data.MolliePaymentDTO
import javax.persistence.*

@Entity
@Table(name = "MolliePayments")
data class MolliePayment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    var status: String? = null,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    val session: Session? = null
) {
    override fun toString(): String {
        return "MolliePayment(molliePaymentId:$molliePaymentId, amount:$amount, createdAt:$createdAt, description:$description, " +
                "expiresAt:$expiresAt, id:$id, isCancelable:$isCancelable, method:$method, mode:$mode, profileId:$profileId, checkoutUrl:$checkoutUrl, " +
                "redirectUrl:$redirectUrl, webhookUrl:$webhookUrl, resource:$resource, sequenceType:$sequenceType, status:$status, " +
                "session: ${session?.id})"
    }

    fun convertToMolliePaymentDTO(): MolliePaymentDTO {
        return MolliePaymentDTO(
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
            sessionId = this.session?.id
        )
    }
}