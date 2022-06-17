package com.example.visible_guitar.viewmodel.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.common.AuthResult
import com.example.domain.common.Resource
import com.example.domain.common.SignUpResult
import com.example.domain.interactor.SignInUseCase
import com.example.domain.mapper.Mapper
import com.example.domain.model.auth.AuthRequestEntity
import com.example.visible_guitar.common.extensions.launchCoroutine
import com.example.visible_guitar.model.auth.AuthRequest
import com.example.visible_guitar.model.event.UserSignInFieldsEvent
import com.example.visible_guitar.model.states.SignInState
import com.example.visible_guitar.model.states.State
import com.example.visible_guitar.viewmodel.base.BaseDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
   private val signInUseCase: SignInUseCase,
   private val authUserMapper: Mapper<AuthRequest, AuthRequestEntity>
) :  ViewModel() {

   private var stateOfFields by mutableStateOf(SignInState())
   private val _signInChannel = Channel<AuthResult<Unit>>()
   val signInState = _signInChannel.receiveAsFlow()

   fun onSignInEvent(event: UserSignInFieldsEvent) {
      when(event) {
         is UserSignInFieldsEvent.EmailChanged ->
            stateOfFields = stateOfFields.copy(email = event.value)
         is UserSignInFieldsEvent.PasswordChanged ->
            stateOfFields = stateOfFields.copy(password = event.value)
         is UserSignInFieldsEvent.SignIn -> signIn()
      }
   }

   private fun signIn() {
      val authRequest = AuthRequest(
         stateOfFields.email,
         stateOfFields.password
      )
      launchCoroutine {
         signInUseCase(
            authUserMapper.convert(authRequest)
         ).collect { result -> _signInChannel.send(result) }
      }
   }

}