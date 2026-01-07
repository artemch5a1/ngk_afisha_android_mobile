package com.example.ngkafisha.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.identityService.userContext.models.Post
import com.example.domain.identityService.userContext.models.Publisher
import com.example.ngkafisha.application.identityService.userContext.useCases.postUseCases.GetAllPostUseCase
import com.example.ngkafisha.application.identityService.userContext.useCases.publisherUseCases.GetCurrentPublisher
import com.example.ngkafisha.application.identityService.userContext.useCases.publisherUseCases.UpdatePublisherUseCase
import com.example.ngkafisha.presentation.models.states.ActualState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPublisherProfileViewModel @Inject constructor(
    private val getCurrentPublisher: GetCurrentPublisher,
    private val getAllPostUseCase: GetAllPostUseCase,
    private val updatePublisherUseCase: UpdatePublisherUseCase
) : ViewModel() {

    private val _actualState = MutableStateFlow<ActualState>(ActualState.Loading)
    val actualState: StateFlow<ActualState> = _actualState

    private val _publisherState = MutableStateFlow<ActualState>(ActualState.Initialized)
    val publisherState: StateFlow<ActualState> = _publisherState

    private val _publisher = MutableStateFlow<Publisher?>(null)
    val publisher: StateFlow<Publisher?> = _publisher

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    fun loadData() {
        viewModelScope.launch {
            _actualState.value = ActualState.Loading

            val publisherResult = getCurrentPublisher(Unit)
            val postsResult = getAllPostUseCase(Unit)

            if (!publisherResult.isSuccess || !postsResult.isSuccess) {
                _actualState.value =
                    ActualState.Error("Ошибка загрузки профиля организатора")
                return@launch
            }

            _publisher.value = publisherResult.value!!
            _posts.value = postsResult.value!!

            _actualState.value = ActualState.Success("")
        }
    }

    fun updatePost(post: Post) {
        _publisher.value = _publisher.value?.copy(
            postId = post.postId,
            post = post
        )
    }

    fun saveChanges() {
        val currentPublisher = _publisher.value ?: return

        viewModelScope.launch {
            _publisherState.value = ActualState.Loading

            val result = updatePublisherUseCase(
                UpdatePublisherUseCase.Request(currentPublisher)
            )

            _publisherState.value =
                if (result.isSuccess) {
                    ActualState.Success("")
                } else {
                    ActualState.Error(
                        result.errorMessage
                    )
                }
        }
    }
}