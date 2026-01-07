package com.example.ngkafisha.di

import com.example.ngkafisha.application.identityService.accountContext.services.auth.Session
import com.example.ngkafisha.application.identityService.accountContext.useCases.LoginUseCase
import com.example.ngkafisha.data.common.utils.OkHttpFileUploader
import com.example.ngkafisha.data.eventService.remote.EventApi
import com.example.ngkafisha.data.eventService.repositories.EventRepositoryImpl
import com.example.ngkafisha.data.eventService.repositories.EventRoleRepositoryImpl
import com.example.ngkafisha.data.eventService.repositories.EventTypeRepositoryImpl
import com.example.ngkafisha.data.eventService.repositories.GenreRepositoryImpl
import com.example.ngkafisha.data.eventService.repositories.InvitationRepositoryImpl
import com.example.ngkafisha.data.eventService.repositories.LocationRepositoryImpl
import com.example.ngkafisha.data.eventService.repositories.MemberRepositoryImpl
import com.example.ngkafisha.data.identityService.accountContext.repositories.AccountRepositoryImpl
import com.example.ngkafisha.data.identityService.common.remote.IdentityApi
import com.example.ngkafisha.data.identityService.userContext.repositories.GroupRepositoryImpl
import com.example.ngkafisha.data.identityService.userContext.repositories.PostRepositoryImpl
import com.example.ngkafisha.data.identityService.userContext.repositories.PublisherRepositoryImpl
import com.example.ngkafisha.data.identityService.userContext.repositories.StudentRepositoryImpl
import com.example.ngkafisha.data.identityService.userContext.repositories.UserRepositoryImpl
import com.example.ngkafisha.domain.common.abstractions.utils.FileUploader
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventRepository
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventRoleRepository
import com.example.ngkafisha.domain.eventService.abstractions.repositories.EventTypeRepository
import com.example.ngkafisha.domain.eventService.abstractions.repositories.GenreRepository
import com.example.ngkafisha.domain.eventService.abstractions.repositories.InvitationRepository
import com.example.ngkafisha.domain.eventService.abstractions.repositories.LocationRepository
import com.example.ngkafisha.domain.eventService.abstractions.repositories.MemberRepository
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.repositories.AccountRepository
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionInfoStore
import com.example.ngkafisha.domain.identityService.accountContext.abstractions.service.auth.SessionStoreService
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.GroupRepository
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.PostRepository
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.PublisherRepository
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.StudentRepository
import com.example.ngkafisha.domain.identityService.userContext.abstractions.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FileUploadModule {

    @Binds
    @Singleton
    abstract fun bindFileUploader(implementation: OkHttpFileUploader): FileUploader
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePostRepository(identityApi: IdentityApi): PostRepository
    {
        return PostRepositoryImpl(identityApi)
    }

    @Provides
    @Singleton
    fun providePublisherRepository(identityApi: IdentityApi): PublisherRepository
    {
        return PublisherRepositoryImpl(identityApi)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(identityApi: IdentityApi): StudentRepository
    {
        return StudentRepositoryImpl(identityApi)
    }

    @Provides
    @Singleton
    fun provideMemberRepository(eventApi: EventApi): MemberRepository
    {
        return MemberRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideEventRoleRepository(eventApi: EventApi): EventRoleRepository
    {
        return EventRoleRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideInvitationRepository(eventApi: EventApi): InvitationRepository
    {
        return InvitationRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventApi: EventApi): EventRepository
    {
        return EventRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideGenreRepository(eventApi: EventApi): GenreRepository
    {
        return GenreRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(eventApi: EventApi): LocationRepository
    {
        return LocationRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideEventTypeRepository(eventApi: EventApi): EventTypeRepository
    {
        return EventTypeRepositoryImpl(eventApi)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(identityApi: IdentityApi): AccountRepository
    {
        return AccountRepositoryImpl(identityApi)
    }

    @Provides
    @Singleton
    fun provideGroupRepository(identityApi: IdentityApi): GroupRepository
    {
        return GroupRepositoryImpl(identityApi)
    }

    @Provides
    @Singleton
    fun provideUserRepository(identityApi: IdentityApi): UserRepository
    {
        return UserRepositoryImpl(identityApi)
    }


    @Provides
    @Singleton
    fun provideSession(): Session = Session()

    @Provides
    @Singleton
    fun provideSessionInfoStore(session: Session): SessionInfoStore = session

    @Provides
    @Singleton
    fun provideSessionStoreService(session: Session): SessionStoreService = session

    @Provides
    @Singleton
    fun provideLoginUseCase(accountRepository: AccountRepository, sessionStoreService: SessionStoreService): LoginUseCase {
        return LoginUseCase(accountRepository, sessionStoreService)
    }
}