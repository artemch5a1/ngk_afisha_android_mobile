package com.example.ngkafisha.data.identityService.userContext.repositories

import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.identityService.common.remote.IdentityApi
import com.example.ngkafisha.data.identityService.userContext.mapper.GroupMapper
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.GroupRepository
import com.example.ngkafisha.domain.identityService.userContext.models.Group
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val identityApi: IdentityApi
) : GroupRepository {

    override suspend fun getAllGroups(): List<Group> {

        return safeApiCall {
            val response = identityApi.getAllGroupsAsync()

            GroupMapper.toListDomain(response)
        }

    }

    override suspend fun getGroupById(groupId: Int): Group {

        return safeApiCall {
            val response = identityApi.getGroupByIdAsync(groupId)

            GroupMapper.toDomain(response)
        }

    }

}