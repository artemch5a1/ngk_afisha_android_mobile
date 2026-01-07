package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.ngkafisha.data.identityService.userContext.dto.PublisherDto
import com.example.ngkafisha.data.identityService.userContext.dto.UpdatePublisherProfileDto
import com.example.ngkafisha.domain.identityService.userContext.models.Publisher

object PublisherMapper {

    fun toDomain(publisherDto: PublisherDto) : Publisher{

        return Publisher(
            publisherId = publisherDto.publisherId,
            user = UserMapper.toDomain(publisherDto.user),
            postId = publisherDto.postId,
            post = PostMapper.toDomain(publisherDto.post)
        )

    }

    fun toListDomain(publishersDto: List<PublisherDto>) : List<Publisher>{

        return publishersDto.map { publisherDto -> toDomain(publisherDto) }

    }

    fun toUpdatePublisher(publisher: Publisher) : UpdatePublisherProfileDto{

        return UpdatePublisherProfileDto(
            publisher.postId
        )

    }
}