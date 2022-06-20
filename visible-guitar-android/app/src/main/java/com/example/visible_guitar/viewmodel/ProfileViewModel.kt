package com.example.visible_guitar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.interactor.GetCurrentUserUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.UserEntity
import com.example.visible_guitar.model.User
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val userMapper: Mapper<UserEntity, User>
) : BaseDetailViewModel() {

    private val _currentUserState = MutableLiveData<State<User>>()
    val currentUserState: LiveData<State<User>> = _currentUserState

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        getCurrentUserUseCase(Unit).subscribeOnCollect(userMapper, _currentUserState)
    }

}