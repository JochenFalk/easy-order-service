package com.easysystems.easyorderservice.services

import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.entities.Session
import com.easysystems.easyorderservice.exceptions.SessionNotFoundException
import com.easysystems.easyorderservice.repositories.SessionRepository
import mu.KLogging
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class SessionService(val sessionRepository: SessionRepository) {

    companion object : KLogging()

    fun createSession(sessionDTO: SessionDTO): SessionDTO {

        val session = sessionDTO.let {
            Session(null, it.status.toString())
        }

        sessionRepository.save(session)

        logger.info("New session created: $session")

        return session.let {
            SessionDTO(it.id, SessionDTO.Status.valueOf(it.status))
        }
    }

    fun retrieveSessionById(id: Int): SessionDTO {

        val session = sessionRepository.findById(id)

        return if (session.isPresent) {
            session.get().let {
                SessionDTO(it.id, SessionDTO.Status.valueOf(it.status))
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }

    fun retrieveOptionalSessionById(id: Int): Optional<Session> {
        return sessionRepository.findById(id)
    }

    fun retrieveAllSessions(): ArrayList<SessionDTO> {

        return sessionRepository.findAll()
            .map {
                SessionDTO(it.id, SessionDTO.Status.valueOf(it.status))
            } as ArrayList<SessionDTO>
    }

    fun updateSession(id: Int, sessionDTO: SessionDTO): SessionDTO {

        val session = sessionRepository.findById(id)

        return if (session.isPresent) {
            session.get().let {
                it.status = sessionDTO.status.toString()
                sessionRepository.save(it)

                SessionDTO(it.id, SessionDTO.Status.valueOf(it.status))
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }

    fun deleteSession(id: Int) {

        val order = sessionRepository.findById(id)

        if (order.isPresent) {
            order.get().let {
                sessionRepository.deleteById(id)
            }
        } else {
            throw SessionNotFoundException("No session found for given id: $id")
        }
    }
}
