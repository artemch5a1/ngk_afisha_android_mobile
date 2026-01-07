package com.example.ngkafisha.data.identityService.common.remote

import com.example.ngkafisha.data.identityService.accountContext.dto.AccountDto
import com.example.ngkafisha.data.identityService.accountContext.dto.ChangePasswordDto
import com.example.ngkafisha.data.identityService.accountContext.dto.CreatedAccountDto
import com.example.ngkafisha.data.identityService.accountContext.dto.LoginRequest
import com.example.ngkafisha.data.identityService.accountContext.dto.LoginResponseDto
import com.example.ngkafisha.data.identityService.accountContext.dto.RegistryStudentDto
import com.example.ngkafisha.data.identityService.userContext.dto.GroupDto
import com.example.ngkafisha.data.identityService.userContext.dto.PostDto
import com.example.ngkafisha.data.identityService.userContext.dto.PublisherDto
import com.example.ngkafisha.data.identityService.userContext.dto.StudentDto
import com.example.ngkafisha.data.identityService.userContext.dto.UpdatePublisherProfileDto
import com.example.ngkafisha.data.identityService.userContext.dto.UpdateStudentProfileDto
import com.example.ngkafisha.data.identityService.userContext.dto.UpdateUserDto
import com.example.ngkafisha.data.identityService.userContext.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface IdentityApi {

    @POST("AccountActions/Login")
    suspend fun loginAsync(@Body request: LoginRequest): LoginResponseDto

    @POST("AccountActions/RegistryStudent")
    suspend fun registryStudent(@Body request: RegistryStudentDto) : CreatedAccountDto

    @GET("AccountActions/CurrentAccount")
    suspend fun getCurrentAccountAsync(): AccountDto



    @GET("UserActions/CurrentUser")
    suspend fun getCurrentUserAsync(): UserDto


    @PUT("UserActions/UpdateStudentProfile")
    suspend fun updateStudentProfile(
        @Body updateStudentProfileDto: UpdateStudentProfileDto
    ) : UUID


    @PUT("UserActions/UpdatePublisherProfile")
    suspend fun updatePublisherProfile(
        @Body updatePublisherProfileDto: UpdatePublisherProfileDto
    ) : UUID

    @PUT("UserActions/UpdateUserInfo")
    suspend fun updateUserInfo(
        @Body updateUserDto: UpdateUserDto
    ) : UUID

    @GET("GroupActions/GetAllGroup")
    suspend fun getAllGroupsAsync() : List<GroupDto>

    @GET("GroupActions/GetGroupById/{id}")
    suspend fun getGroupByIdAsync(
        @Path("id") id: Int
    ): GroupDto


    @GET("StudentActions/GetStudentById/{studentId}")
    suspend fun getStudentById(
        @Path("studentId") studentId: UUID
    ) : StudentDto

    @GET("StudentActions/CurrentStudent")
    suspend fun getCurrentStudentAsync(
    ) : StudentDto

    @GET("PublisherActions/CurrentPublisher")
    suspend fun getCurrentPublisherAsync() : PublisherDto

    @PATCH("AccountActions/ChangePassword")
    suspend fun changePasswordAsync(
        @Body changePasswordDto: ChangePasswordDto
    ) : UUID

    @GET("PostActions/GetAllPost")
    suspend fun getAllPostsAsync() : List<PostDto>
}