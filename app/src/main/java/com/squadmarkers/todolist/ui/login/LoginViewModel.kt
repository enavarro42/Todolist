package com.squadmarkers.todolist.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squadmarkers.todolist.Application
import com.squadmarkers.todolist.data.FirebaseRepository
import com.squadmarkers.todolist.ui.signup.SignupState
import com.squadmarkers.todolist.utils.ManagePassword
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository,
    val managePassword: ManagePassword
) : ViewModel() {

    private val networkScope = CoroutineScope(Dispatchers.IO)

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val stateLogin = MutableLiveData<LoginState>()

    fun getStateLogin() : LiveData<LoginState> = stateLogin

    fun login(context: Context, user: String, password: String) {
        if (user.isNotEmpty() && password.isNotEmpty()) {
            networkScope.launch(errorHandler) {
                firebaseRepository.getUser(user.trim())
                    .addOnSuccessListener { task ->
                        val decryptPassword = managePassword.decrypt(task.password)
                        if (task != null && task.userName == user && decryptPassword == password) {
                            Application.preference?.currentUser = user
                            stateLogin.postValue(LoginState.OnSuccess)
                        } else {
                            stateLogin.postValue(LoginState.InvalidLogin)
                        }
                    }
                    .addOnFailureListener { exception ->
                        stateLogin.postValue(LoginState.Fail(exception.message.orEmpty()))
                    }
            }
        } else {
            stateLogin.postValue(LoginState.EmptyFields)
        }
    }

}