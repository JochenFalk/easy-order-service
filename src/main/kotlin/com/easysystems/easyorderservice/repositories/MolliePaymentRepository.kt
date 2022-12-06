package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.MolliePayment
import org.springframework.data.repository.CrudRepository


interface MolliePaymentRepository : CrudRepository<MolliePayment, Int> {

}