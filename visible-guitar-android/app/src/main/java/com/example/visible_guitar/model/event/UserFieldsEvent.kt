package com.example.visible_guitar.model.event

sealed class UserSignUpFieldsEvent {
    data class UsernameChanged(val value: String): UserSignUpFieldsEvent()
    data class EmailChanged(val value: String): UserSignUpFieldsEvent()
    data class PasswordChanged(val value: String): UserSignUpFieldsEvent()
    object SignUp: UserSignUpFieldsEvent()
}

sealed class UserSignInFieldsEvent {
    data class EmailChanged(val value: String): UserSignInFieldsEvent()
    data class PasswordChanged(val value: String): UserSignInFieldsEvent()
    object SignIn: UserSignInFieldsEvent()
}