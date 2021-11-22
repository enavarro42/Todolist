package com.squadmarkers.todolist.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squadmarkers.todolist.data.FirebaseRepository
import com.squadmarkers.todolist.utils.ManagePassword
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository,
    val managePassword: ManagePassword
) : ViewModel() {

    private val networkScope = CoroutineScope(Dispatchers.IO)

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val stateRegister = MutableLiveData<SignupState>()

    fun getStateRegister() : LiveData<SignupState> = stateRegister

    fun validateUser(user: String, password: String, confirmPassword: String) {
        if (user.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            networkScope.launch(errorHandler) {
                val passwordEncrypt = managePassword.encrypt(password)
                firebaseRepository.getUser(user.trim())
                    .addOnSuccessListener { task ->
                        if (task != null) {
                            stateRegister.postValue(SignupState.UserExist)
                        } else {
                            createUser(user, passwordEncrypt.orEmpty(), password, confirmPassword)
                        }
                    }
                    .addOnFailureListener { exception ->
                        stateRegister.postValue(SignupState.Fail(exception.message.orEmpty()))
                    }
            }
        } else {
            stateRegister.value = SignupState.EmptyFields
        }
    }

    private fun createUser(user: String, passwordEncrypt: String, password: String, confirmPassword: String) {
        networkScope.launch(errorHandler) {
            if (password == confirmPassword) {
                val result = firebaseRepository.createUser(user, passwordEncrypt)
                if (result) {
                    stateRegister.postValue(SignupState.OnSuccess)
                } else {
                    stateRegister.postValue(SignupState.ErrorCreatingUser)
                }
            } else {
                stateRegister.postValue(SignupState.PasswordNotMatch)
            }
        }
    }

}