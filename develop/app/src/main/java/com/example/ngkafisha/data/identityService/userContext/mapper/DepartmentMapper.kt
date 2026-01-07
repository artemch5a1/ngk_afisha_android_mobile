package com.example.ngkafisha.data.identityService.userContext.mapper

import com.example.domain.identityService.userContext.models.Department
import com.example.ngkafisha.data.identityService.userContext.dto.DepartmentDto

object DepartmentMapper {

    fun toDomain(departmentDto: DepartmentDto) : Department {

        return Department(
            departmentId = departmentDto.departmentId,
            title = departmentDto.title
        )

    }

    fun toListDomain(departmentsDto: List<DepartmentDto>) : List<Department>{

        return departmentsDto.map { departmentDto -> toDomain(departmentDto) }

    }

}