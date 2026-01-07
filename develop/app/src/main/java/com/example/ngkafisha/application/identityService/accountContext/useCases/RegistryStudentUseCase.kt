package com.example.ngkafisha.application.identityService.accountContext.useCases

import com.example.domain.common.models.CustomResult
import com.example.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.domain.identityService.userContext.models.Student
import com.example.ngkafisha.application.common.base.BaseUseCase
import com.example.ngkafisha.application.common.utils.isEmailValid
import java.time.LocalDate
import javax.inject.Inject

class RegistryStudentUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    sessionStoreService: SessionStoreService
) : BaseUseCase<RegistryStudentUseCase.Request, Unit>(sessionStoreService) {
    data class Request(val email: String,
                       val password: String,
                       val repeatPassword: String,
                       val student: Student
    )

    override suspend fun invokeLogic(request: Request): CustomResult<Unit> {
        val validation: CustomResult<Request> = validate(request)

        if(!validation.isSuccess)
            return CustomResult.failure(validation.errorMessage)

        accountRepository.registryStudent(
            request.email,
            request.password,
            request.student)

        return CustomResult.success(Unit)
    }

    private fun validate(request: Request) : CustomResult<Request>
    {
        val hasEmptyFields = request.email.isBlank()
                || request.password.isBlank()
                || request.student.user.surname.isBlank()
                || request.student.user.name.isBlank()
                || request.student.user.patronymic.isBlank()

        if (hasEmptyFields) {
            return CustomResult.failure("Есть незаполненные поля")
        }

        if (request.password != request.repeatPassword) {
            return CustomResult.failure("Пароли не совпадают")
        }

        if (request.student.groupId <= -1) {
            return CustomResult.failure("Группа не выбрана")
        }

        if(!request.email.isEmailValid()){
            return CustomResult.failure("Неверный формат email")
        }

        val now = LocalDate.now()
        if (request.student.user.birthDate.isAfter(now)) {
            return CustomResult.failure("Дата рождения не может быть в будущем")
        }

        if (request.student.user.birthDate.isBefore(LocalDate.of(1900, 1, 1))) {
            return CustomResult.failure("Дата рождения указана некорректно")
        }

        return CustomResult.success(request)
    }
}