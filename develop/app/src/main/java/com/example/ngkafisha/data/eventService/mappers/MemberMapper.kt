package com.example.ngkafisha.data.eventService.mappers

import com.example.ngkafisha.data.eventService.dto.invitations.MemberDto
import com.example.ngkafisha.domain.common.enums.MemberStatus
import com.example.ngkafisha.domain.eventService.models.Member

object MemberMapper {

    fun toDomain(memberDto: MemberDto) : Member{

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