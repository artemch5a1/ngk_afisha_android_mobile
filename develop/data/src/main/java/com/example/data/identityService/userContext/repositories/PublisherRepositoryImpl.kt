package com.example.data.identityService.userContext.repositories

import com.example.data.common.apiMethods.safeApiCall
import com.example.data.identityService.common.remote.IdentityApi
import com.example.data.identityService.userContext.mapper.PublisherMapper
import com.example.domain.identityService.userContext.abstractions.repositories.PublisherRepository
import com.example.domain.identityService.userContext.models.Publisher
import java.util.UUID
import javax.inject.Inject

class PublisherRepositoryImpl @Inject constructor(
    private val identityApi: IdentityApi
) : PublisherRepository {


    override suspend fun getCurrentPublisher(): Publisher {
        return safeApiCall {

            val response = identityApi.getCurrentPublisherAsync()

            PublisherMapper.toDomain(response)

        }
    }

    override suspend fun updatePublisher(publisher: Publisher): UUID {
        return safeApiCall{

            identityApi.updatePublisherProfile(
                PublisherMapper.toUpdatePublisher(publisher)
            )

        }
    }


}