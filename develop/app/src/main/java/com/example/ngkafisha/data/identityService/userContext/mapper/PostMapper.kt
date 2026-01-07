package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.ngkafisha.data.identityService.userContext.dto.PostDto
import com.example.ngkafisha.domain.identityService.userContext.models.Post

object PostMapper {

    fun toDomain(postDto: PostDto) : Post{

        return Post(
            postId = postDto.postId,
            title = postDto.title,
            departmentId = postDto.departmentId,
            department = DepartmentMapper.toDomain(postDto.department)
        )

    }

    fun toDomainList(postsDto: List<PostDto>) : List<Post>{

        return postsDto.map { postDto -> toDomain(postDto) }

    }

}