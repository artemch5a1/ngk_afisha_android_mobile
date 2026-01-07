package com.example.ngkafisha.data.eventService.remote

import com.example.ngkafisha.data.eventService.dto.events.CreateEventDto
import com.example.ngkafisha.data.eventService.dto.events.CreatedEventDto
import com.example.ngkafisha.data.eventService.dto.events.EventDto
import com.example.ngkafisha.data.eventService.dto.events.EventTypeDto
import com.example.ngkafisha.data.eventService.dto.events.GenreDto
import com.example.ngkafisha.data.eventService.dto.events.LocationDto
import com.example.ngkafisha.data.eventService.dto.events.UpdateEventDto
import com.example.ngkafisha.data.eventService.dto.events.UpdatedEventDto
import com.example.ngkafisha.data.eventService.dto.invitations.AcceptRequestOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.CancelRequestOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.CreateInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.EventRoleDto
import com.example.ngkafisha.data.eventService.dto.invitations.InvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.MemberDto
import com.example.ngkafisha.data.eventService.dto.invitations.RejectMemberOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.TakeRequestOnInvitationDto
import com.example.ngkafisha.data.eventService.dto.invitations.UpdateInvitationDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface EventApi {

    @GET("EventActions/GetAllEvent")
    suspend fun getAllEventsAsync(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ): List<EventDto>

    @GET("EventActions/GetEventById/{eventId}")
    suspend fun getEventByIdAsync(
        @Path("eventId") eventId: UUID
    ): EventDto

    @GET("EventActions/GetAllEventByAuthor")
    suspend fun getAllEventByAuthorAsync(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ): List<EventDto>

    @POST("EventActions/CreateEvent")
    suspend fun createEventAsync(@Body createEventDto: CreateEventDto): CreatedEventDto

    @PUT("EventActions/UpdateEvent")
    suspend fun updateEventAsync(@Body updateEventDto: UpdateEventDto): UpdatedEventDto

    @DELETE("EventActions/DeleteEvent/{eventId}")
    suspend fun deleteEventAsync(
        @Path("eventId") eventId: UUID
    ) : UUID

    @GET("EventTypeActions/GetAllEventType")
    suspend fun getAllEventTypeAsync(): List<EventTypeDto>


    @GET("GenresActions/GetAllGenre")
    suspend fun getAllGenreAsync() : List<GenreDto>


    @GET("LocationActions/GetAllLocation")
    suspend fun getAllLocationAsync() : List<LocationDto>


    @GET("EventRoleActions/GetAllEventRole")
    suspend fun getAllEventRolesAsync(): List<EventRoleDto>

    @GET("InvitationAction/GetAllActualInvitation")
    suspend fun getAllInvitationAsync(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ): List<InvitationDto>

    @GET("InvitationAction/GetAllInvitationByAuthor")
    suspend fun getAllInvitationByAuthorAsync(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ): List<InvitationDto>

    @GET("InvitationAction/GetAllInvitationByEvent/{eventId}")
    suspend fun getAllInvitationByEventAsync(
        @Path("eventId") eventId: UUID,
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ): List<InvitationDto>

    @GET("InvitationAction/GetInvitationById/{invitationId}")
    suspend fun getInvitationByIdAsync(
        @Path("invitationId") eventId: UUID
    ) : InvitationDto

    @POST("InvitationAction/CreateInvitation")
    suspend fun createInvitationAsync(
        @Body createInvitationDto: CreateInvitationDto
    )

    @PUT("InvitationAction/UpdateInvitation")
    suspend fun updateInvitationAsync(
        @Body updateInvitationDto: UpdateInvitationDto
    ) : UUID


    @DELETE("InvitationAction/DeleteInvitation")
    suspend fun deleteInvitationAsync(
        @Query("eventId") eventId: UUID,
        @Query("invitationId") invitationId: UUID
    ) : UUID


    @POST("InvitationAction/AcceptRequestOnInvitation")
    suspend fun acceptRequestAsync(
        @Body acceptRequestOnInvitationDto: AcceptRequestOnInvitationDto
    ) : UUID

    @POST("InvitationAction/TakeRequestOnInvitation")
    suspend fun takeRequestAsync(
        @Body takeRequestOnInvitationDto: TakeRequestOnInvitationDto
    ) : UUID

    @POST("InvitationAction/CancelRequestOnInvitation")
    suspend fun cancelRequestAsync(
        @Body cancelRequestOnInvitationDto: CancelRequestOnInvitationDto
    ) : UUID

    @POST("InvitationAction/RejectMemberOnOnInvitation")
    suspend fun rejectRequestAsync(
        @Body rejectMemberOnInvitationDto: RejectMemberOnInvitationDto
    ) : UUID

    @GET("MemberAction/GetAllMemberByAuthor")
    suspend fun getAllMemberByAuthorAsync(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ) : List<MemberDto>

    @GET("MemberAction/GetAllMemberByStudent")
    suspend fun getAllMemberByStudentAsync(
        @Query("skip") skip: Int = 0,
        @Query("take") take: Int = Int.MAX_VALUE
    ) : List<MemberDto>
}