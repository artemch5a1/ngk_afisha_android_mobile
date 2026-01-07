package com.example.ngkafisha.data.eventService.repositories

import com.example.domain.eventService.abstractions.repositories.MemberRepository
import com.example.domain.eventService.models.Member
import com.example.ngkafisha.data.common.apiMethods.safeApiCall
import com.example.ngkafisha.data.eventService.mappers.MemberMapper
import com.example.ngkafisha.data.eventService.remote.EventApi
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val eventApi: EventApi
) : MemberRepository {
    override suspend fun getAllMemberByAuthor(skip: Int, take: Int) : List<Member> {
        return safeApiCall {

            val response = eventApi.getAllMemberByAuthorAsync(skip, take)

            MemberMapper.toListDomain(response)
        }
    }

    override suspend fun getAllMemberByStudent(skip: Int, take: Int) : List<Member> {
        return safeApiCall {

            val response = eventApi.getAllMemberByStudentAsync(skip, take)

            MemberMapper.toListDomain(response)
        }
    }
}