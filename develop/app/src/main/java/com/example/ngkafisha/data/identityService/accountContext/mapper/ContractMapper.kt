package com.example.ngkafisha.data.identityService.accountContext.mapper

import com.example.ngkafisha.data.identityService.accountContext.dto.ChangePasswordDto
import com.example.ngkafisha.domain.identityService.accountContext.contracts.ChangePassword

object ContractMapper {

    fun toChangePasswordDto(changePassword: ChangePassword) : ChangePasswordDto{

        return ChangePasswordDto(
            oldPassword = changePassword.oldPassword,
            newPassword = changePassword.newPassword
        )

    }

}