package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.Session
import org.springframework.data.repository.CrudRepository

interface SessionRepository : CrudRepository<Session, Int> {

}
