package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.domain.identityService.userContext.models.Publisher
import com.example.ngkafisha.data.identityService.userContext.dto.PublisherDto
import com.example.ngkafisha.data.identityService.userContext.dto.UpdatePublisherProfileDto

object PublisherMapper {

    fun toDomain(publisherDto: PublisherDto) : Publisher {

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