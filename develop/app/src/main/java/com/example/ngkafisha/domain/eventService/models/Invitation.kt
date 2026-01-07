package com.example.ngkafisha.domain.eventService.models

import com.example.ngkafisha.domain.common.enums.InvitationStatus
import java.time.LocalDateTime
import java.util.UUID

data class Invitation(
    val invitationId: UUID,

    val eventId: UUID,

    val event: Event? = null,

    val roleId: Int,

    val role: EventRole? = null,

    val shortDescription: String,

    val description: String,

    val requiredMember: Int,

    val acceptedMember: Int,

    val deadLine: LocalDateTime,

    val status: InvitationStatus = InvitationStatus.ACTIVE
)
