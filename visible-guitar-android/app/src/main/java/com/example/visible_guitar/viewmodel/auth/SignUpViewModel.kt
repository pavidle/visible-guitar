package com.example.visible_guitar.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.interactor.CreateANewUserUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.SignUpRequestEntity
import com.example.domain.common.SignUpResult
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.auth.SignUpRequest
import com.example.visible_guitar.model.event.UserSignUpFieldsEvent
import com.example.visible_guitar.model.states.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createANewUserUseCase: CreateANewUserUseCase,
    private val signUpRequestMapper: Mapper<SignUpRequest, SignUpRequestEntity>
) : ViewModel() {

    private var stateOfFields by mutableStateOf(SignUpState())
    private val _signUpChannel = Channel<SignUpResult<Unit>>()
    val signUpState = _signUpChannel.receiveAsFlow()

    fun onSignUpEvent(event: UserSignUpFieldsEvent) = when(event) {
            is UserSignUpFieldsEvent.UsernameChanged ->
                stateOfFields = stateOfFields.copy(username = event.value)
            is UserSignUpFieldsEvent.EmailChanged ->
                stateOfFields = stateOfFields.copy(email = event.value)
            is UserSignUpFieldsEvent.PasswordChanged ->
                stateOfFields = stateOfFields.copy(password = event.value)
            is UserSignUpFieldsEvent.SignUp -> signUp()
        }


    private fun signUp() {
        launchCoroutine {
            val signUpRequest = SignUpRequest(
                stateOfFields.username,
                stateOfFields.email,
                stateOfFields.password
            )
            createANewUserUseCase(
                signUpRequestMapper.convert(signUpRequest)
            ).collect { result -> _signUpChannel.send(result) }
        }
    }
}