package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.MolliePayment
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MolliePaymentRepository : CrudRepository<MolliePayment, Int> {

    @Query(value = "SELECT * FROM MOLLIE_PAYMENTS WHERE id = ?1", nativeQuery = true)
    fun findByMollieId(id: String): MolliePayment?
}