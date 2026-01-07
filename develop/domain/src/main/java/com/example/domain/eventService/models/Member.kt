package com.example.domain.eventService.models


import com.example.domain.common.enums.MemberStatus
import com.example.domain.identityService.userContext.models.Student
import java.util.UUID

data class Member(
    val invitationId: UUID,

    val invitation: Invitation,

    val studentId: UUID,

    val status: MemberStatus,

    var student: Student? = null
)
