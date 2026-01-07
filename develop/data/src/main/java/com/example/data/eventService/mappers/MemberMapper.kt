package com.example.data.eventService.mappers

import com.example.data.eventService.dto.invitations.MemberDto
import com.example.domain.common.enums.MemberStatus
import com.example.domain.eventService.models.Member
import kotlin.collections.map

object MemberMapper {

    fun toDomain(memberDto: MemberDto) : Member {

        return Member(
            invitationId = memberDto.invitationId,
            invitation = InvitationMapper.toDomain(memberDto.invitation),
            studentId = memberDto.studentId,
            status = MemberStatus.fromInt(memberDto.status)
        )

    }

    fun toListDomain(memberDtos: List<MemberDto>) : List<Member> {

        return memberDtos.map { memberDto -> toDomain(memberDto) }

    }

}