package com.example.data.eventService.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.eventService.mappers.InvitationMapper
import com.example.data.eventService.remote.EventApi
import com.example.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.domain.eventService.contracts.AcceptRequestOnInvitation
import com.example.domain.eventService.contracts.CancelRequestOnInvitation
import com.example.domain.eventService.contracts.RejectMemberOnInvitation
import com.example.domain.eventService.contracts.TakeRequestOnInvitation
import com.example.domain.eventService.models.Invitation
import java.util.UUID
import javax.inject.Inject

class InvitationRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : InvitationRepository {


    override suspend fun getAllInvitation(
        skip: Int,
        take: Int
    ): List<Invitation> {

        return safeApiCall {
            val response = eventApi.getAllInvitationAsync(skip, take)

            InvitationMapper.toListDomain(response)
        }

    }

    override suspend fun getAllInvitationByAuthor(skip: Int, take: Int): List<Invitation> {
        return safeApiCall {
            val response = eventApi.getAllInvitationByAuthorAsync(skip, take)

            InvitationMapper.toListDomain(response)
        }
    }

    override suspend fun getAllInvitationByEvent(
        eventId: UUID,
        skip: Int,
        take: Int
    ): List<Invitation> {
        return safeApiCall {
            val response = eventApi.getAllInvitationByEventAsync(eventId, skip, take)

            InvitationMapper.toListDomain(response)
        }
    }

    override suspend fun getInvitationById(invitationId: UUID): Invitation {
        return safeApiCall {
            val response = eventApi.getInvitationByIdAsync(invitationId)

            InvitationMapper.toDomain(response)
        }
    }

    override suspend fun createInvitation(invitation: Invitation) {
        return safeApiCall {
            eventApi.createInvitationAsync(InvitationMapper.toCreateInvitationDto(invitation))
        }
    }

    override suspend fun updateInvitation(invitation: Invitation): UUID {
        return safeApiCall {
            eventApi.updateInvitationAsync(InvitationMapper.toUpdateInvitationDto(invitation))
        }
    }

    override suspend fun deleteInvitation(
        eventId: UUID,
        invitationId: UUID
    ): UUID {
        return safeApiCall {
            eventApi.deleteInvitationAsync(eventId = eventId, invitationId = invitationId)
        }
    }

    override suspend fun acceptRequestOnInvitation(acceptRequestOnInvitation: AcceptRequestOnInvitation): UUID {
        return safeApiCall {
            eventApi.acceptRequestAsync(
                InvitationMapper.toAcceptDto(acceptRequestOnInvitation)
            )
        }
    }

    override suspend fun takeRequestOnInvitation(takeRequestOnInvitation: TakeRequestOnInvitation): UUID {
        return safeApiCall {
            eventApi.takeRequestAsync(
                InvitationMapper.toTakeRequestDto(takeRequestOnInvitation)
            )
        }
    }

    override suspend fun cancelRequestOnInvitation(cancelRequestOnInvitation: CancelRequestOnInvitation): UUID {
        return safeApiCall {
            eventApi.cancelRequestAsync(
                InvitationMapper.toCancelDto(cancelRequestOnInvitation)
            )
        }
    }

    override suspend fun rejectMemberOnInvitation(rejectMemberOnInvitation: RejectMemberOnInvitation): UUID {
        return safeApiCall {
            eventApi.rejectRequestAsync(
                InvitationMapper.toRejectDto(rejectMemberOnInvitation)
            )
        }
    }

}