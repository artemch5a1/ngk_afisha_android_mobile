package com.example.data.eventService.dto.invitations

import java.util.UUID

data class MemberDto(
    val invitationId: UUID,
    val invitation: InvitationDto,
    val studentId: UUID,
    val status:Int
)
