package com.easysystems.easyorderservice.repositories

import com.easysystems.easyorderservice.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {

}