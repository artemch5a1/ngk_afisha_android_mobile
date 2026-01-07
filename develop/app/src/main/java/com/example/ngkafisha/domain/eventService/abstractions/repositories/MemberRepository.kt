package com.example.ngkafisha.domain.eventService.abstractions.repositories

import com.example.ngkafisha.domain.eventService.models.Member

interface MemberRepository {

    suspend fun getAllMemberByAuthor(skip:Int = 0, take:Int = Int.MAX_VALUE) : List<Member>

    suspend fun getAllMemberByStudent(skip:Int = 0, take:Int = Int.MAX_VALUE) : List<Member>

}