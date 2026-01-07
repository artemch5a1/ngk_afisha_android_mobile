package com.example.ngkafisha.data.eventService.mappers

import com.example.domain.common.enums.InvitationStatus
import com.example.domain.eventService.contracts.AcceptRequestOnInvitation
import com.example.domain.eventService.contracts.CancelRequestOnInvitation
import com.example.domain.eventService.contracts.RejectMemberOnInvitation
import com.example.domain.eventService.contracts.TakeRequestOnInvitation
import com.example.domain.eventService.models.Invitation
import com.example.ngkafisha.data.eventService.dto.invitations.AcceptRequestOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.CancelRequestOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.CreateInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.InvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.RejectMemberOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.TakeRequestOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.UpdateInvitationDto
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

object InvitationMapper {

    fun toDomain(invitationDto: InvitationDto) : Invitation {

        return Invitation(
            invitationId = invitationDto.invitationId,
            eventId = invitationDto.eventId,
            event = EventMapper.toDomain(invitationDto.event),
            roleId = invitationDto.roleId,
            role = EventRoleMapper.toDomain(invitationDto.role),
            shortDescription = invitationDto.shortDescription,
            description = invitationDto.description,
            requiredMember = invitationDto.requiredMember,
            acceptedMember = invitationDto.acceptedMember,
            deadLine = convertUtcStringToLocalDateTime(invitationDto.deadLine),
            status = InvitationStatus.fromInt(invitationDto.status)
        )

    }

    fun toListDomain(invitationDtos: List<InvitationDto>) : List<Invitation>{

        return invitationDtos.map { invitation -> toDomain(invitation) }

    }

    fun toCreateInvitationDto(invitation: Invitation) : CreateInvitationDto{

        return CreateInvitationDto(
            eventId = invitation.eventId,
            roleId = invitation.roleId,
            shortDescription = invitation.shortDescription,
            description = invitation.description,
            requiredMember = invitation.requiredMember,
            deadLine = invitation.deadLine.toUtcIso8601String()
        )

    }

    fun toUpdateInvitationDto(invitation: Invitation) : UpdateInvitationDto{
        return UpdateInvitationDto(
            eventId = invitation.eventId,
            invitationId = invitation.invitationId,
            roleId = invitation.roleId,
            shortDescription = invitation.shortDescription,
            description = invitation.description,
            requiredMember = invitation.requiredMember,
            deadLine = invitation.deadLine.toUtcIso8601String()
        )
    }

    fun toAcceptDto(acceptRequestOnInvitation: AcceptRequestOnInvitation) : AcceptRequestOnInvitationDto{

        return AcceptRequestOnInvitationDto(
            invitationId = acceptRequestOnInvitation.invitationId,
            eventId = acceptRequestOnInvitation.eventId,
            studentId = acceptRequestOnInvitation.studentId
        )

    }

    fun toCancelDto(cancelRequestOnInvitation: CancelRequestOnInvitation) : CancelRequestOnInvitationDto {

        return CancelRequestOnInvitationDto(
            invitationId = cancelRequestOnInvitation.invitationId,
            eventId = cancelRequestOnInvitation.eventId
        )

    }

    fun toTakeRequestDto(takeRequestOnInvitation: TakeRequestOnInvitation) : TakeRequestOnInvitationDto {

        return TakeRequestOnInvitationDto(
            eventId = takeRequestOnInvitation.eventId,
            invitationId = takeRequestOnInvitation.invitationId
        )

    }

    fun toRejectDto(rejectMemberOnInvitation: RejectMemberOnInvitation) : RejectMemberOnInvitationDto {

        return RejectMemberOnInvitationDto(
            invitationId = rejectMemberOnInvitation.invitationId,
            eventId = rejectMemberOnInvitation.eventId,
            studentId = rejectMemberOnInvitation.studentId
        )

    }

    private fun convertUtcStringToLocalDateTime(dateStr: String): LocalDateTime {

        val utcDateTime = OffsetDateTime.parse(dateStr)

        return utcDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    }

    private fun LocalDateTime.toUtcIso8601String(): String {
        val zonedDateTime = this.atZone(ZoneId.systemDefault())

        val instant = zonedDateTime.toInstant()

        return instant.toString()
    }
}