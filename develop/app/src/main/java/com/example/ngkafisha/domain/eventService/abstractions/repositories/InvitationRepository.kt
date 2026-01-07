package com.example.ngkafisha.domain.eventService.abstractions.repositories

import com.example.ngkafisha.domain.eventService.contracts.AcceptRequestOnInvitation
import com.example.ngkafisha.domain.eventService.contracts.CancelRequestOnInvitation
import com.example.ngkafisha.domain.eventService.contracts.RejectMemberOnInvitation
import com.example.ngkafisha.domain.eventService.contracts.TakeRequestOnInvitation
import com.example.ngkafisha.domain.eventService.models.Invitation
import java.util.UUID

interface InvitationRepository {

    suspend fun getAllInvitation(skip:Int = 0, take:Int = 50) : List<Invitation>

    suspend fun getAllInvitationByAuthor(skip: Int = 0, take: Int = 50) : List<Invitation>

    suspend fun getAllInvitationByEvent(eventId: UUID, skip: Int = 0, take: Int = 50) : List<Invitation>

    suspend fun getInvitationById(invitationId: UUID) : Invitation

    suspend fun createInvitation(invitation: Invitation)

    suspend fun updateInvitation(invitation: Invitation) : UUID

    suspend fun deleteInvitation(eventId: UUID, invitationId: UUID) : UUID

    suspend fun acceptRequestOnInvitation(acceptRequestOnInvitation: AcceptRequestOnInvitation) : UUID

    suspend fun takeRequestOnInvitation(takeRequestOnInvitation: TakeRequestOnInvitation) : UUID

    suspend fun cancelRequestOnInvitation(cancelRequestOnInvitation: CancelRequestOnInvitation) : UUID

    suspend fun rejectMemberOnInvitation(rejectMemberOnInvitation: RejectMemberOnInvitation) : UUID
}