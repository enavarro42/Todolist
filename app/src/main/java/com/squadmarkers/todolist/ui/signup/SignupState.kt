package com.squadmarkers.todolist.ui.signup

sealed class SignupState {
    object OnSuccess : SignupState()
    class Fail(val errorMessage: String = "") : SignupState()
    object UserExist : SignupState()
    object ErrorCreatingUser : SignupState()
    object PasswordNotMatch : SignupState()
    object EmptyFields : SignupState()
}