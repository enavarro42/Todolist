package com.squadmarkers.todolist.ui.login

import com.squadmarkers.todolist.ui.signup.SignupState

sealed class LoginState {
    object OnSuccess : LoginState()
    object InvalidLogin : LoginState()
    class Fail(val errorMessage: String = "") : LoginState()
    object EmptyFields : LoginState()
}