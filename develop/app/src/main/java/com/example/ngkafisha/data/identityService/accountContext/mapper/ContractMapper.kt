package com.example.ngkafisha.data.identityService.accountContext.mapper

import com.example.domain.identityService.accountContext.contracts.ChangePassword
import com.example.ngkafisha.data.identityService.accountContext.dto.ChangePasswordDto

object ContractMapper {

    fun toChangePasswordDto(changePassword: ChangePassword) : ChangePasswordDto{

        return ChangePasswordDto(
            oldPassword = changePassword.oldPassword,
            newPassword = changePassword.newPassword
        )

    }

}