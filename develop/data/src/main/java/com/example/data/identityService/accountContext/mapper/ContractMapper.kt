package com.example.data.identityService.accountContext.mapper

import com.example.data.identityService.accountContext.dto.ChangePasswordDto
import com.example.domain.identityService.accountContext.contracts.ChangePassword

object ContractMapper {

    fun toChangePasswordDto(changePassword: ChangePassword) : ChangePasswordDto {

        return ChangePasswordDto(
            oldPassword = changePassword.oldPassword,
            newPassword = changePassword.newPassword
        )

    }

}