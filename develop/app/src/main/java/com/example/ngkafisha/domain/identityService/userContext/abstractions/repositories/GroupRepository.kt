package com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories

import com.example.ngkafisha.domain.identityService.userContext.models.Group

interface GroupRepository {

    suspend fun getAllGroups(): List<Group>

    suspend fun getGroupById(groupId:Int) : Group
}