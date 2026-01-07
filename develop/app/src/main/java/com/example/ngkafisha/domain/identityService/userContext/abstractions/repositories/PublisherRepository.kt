package com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories

import com.example.ngkafisha.domain.identityService.userContext.models.Publisher
import java.util.UUID

interface PublisherRepository {

    suspend fun getCurrentPublisher() : Publisher

    suspend fun updatePublisher(publisher: Publisher) : UUID
}