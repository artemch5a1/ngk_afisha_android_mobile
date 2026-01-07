package com.example.domain.identityService.userContext.abstractions.repositories


import com.example.domain.identityService.userContext.models.Publisher
import java.util.UUID

interface PublisherRepository {

    suspend fun getCurrentPublisher() : Publisher

    suspend fun updatePublisher(publisher: Publisher) : UUID
}