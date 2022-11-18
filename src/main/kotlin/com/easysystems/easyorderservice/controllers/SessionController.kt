package com.easysystems.easyorderservice.controllers

import com.easysystems.easyorderservice.data.SessionDTO
import com.easysystems.easyorderservice.services.SessionService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/v1/sessions")
@Validated
class SessionController(val sessionService: SessionService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSession(@RequestBody @Valid sessionDTO: SessionDTO): SessionDTO {
        return sessionService.createSession(sessionDTO)
    }

    @GetMapping("/{id}")
    fun retrieveSessionById(@PathVariable("id") id: Int): SessionDTO {
        return sessionService.retrieveSessionById(id)
    }

    @GetMapping
    fun retrieveAllSessions(): ArrayList<SessionDTO> {
        return sessionService.retrieveAllSessions()
    }

    @PutMapping("/{id}")
    fun updateSession(@RequestBody @Valid sessionDTO: SessionDTO, @PathVariable("id") id: Int): SessionDTO {
        return sessionService.updateSession(id, sessionDTO)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSession(@PathVariable("id") id: Int) {
        return sessionService.deleteSession(id)
    }

    @GetMapping("/{tabletopId}/{authCode}")
    fun verifyAndRetrieveSessionByTabletop(
        @PathVariable("tabletopId") tabletopId: Int,
        @PathVariable("authCode") authCode: String
    ): SessionDTO? {
        return sessionService.retrieveSessionByTabletop(tabletopId, authCode)
    }
}