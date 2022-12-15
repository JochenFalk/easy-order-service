package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Session
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface SessionRepository : CrudRepository<Session, Int> {

    @Query(value = "SELECT * FROM SESSIONS WHERE tabletop_id = ?1 AND status != ?2", nativeQuery = true)
    fun findByTabletopId(tabletopId: Int, status: String): Session?
}