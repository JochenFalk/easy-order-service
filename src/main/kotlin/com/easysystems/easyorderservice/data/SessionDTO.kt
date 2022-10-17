package com.easysystems.easyorderservice.data

data class SessionDTO(
    val id: Int? = 0,
    val status: Status
) {
    enum class Status {
        OPENED,
        CLOSED
    }
}
