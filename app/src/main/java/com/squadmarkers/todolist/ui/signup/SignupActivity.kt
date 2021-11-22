package com.squadmarkers.todolist.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.squadmarkers.todolist.R
import com.squadmarkers.todolist.databinding.ActivityLoginBinding
import com.squadmarkers.todolist.databinding.ActivitySignupBinding
import com.squadmarkers.todolist.di.scope.ActivityScoped
import com.squadmarkers.todolist.ui.login.LoginViewModel
import com.squadmarkers.todolist.utils.ManagePassword
import com.squadmarkers.todolist.utils.hideKeyboard
import com.squadmarkers.todolist.utils.showKeyboard
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@ActivityScoped
class SignupActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var viewModel: SignupViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewModel()
        listeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignupViewModel::class.java)
        viewModel.getStateRegister().observe(this, Observer {
            enableButton(true)
            showProgress(false)
            when(it) {
                is SignupState.OnSuccess -> {
                    Toast.makeText(this, getString(R.string.user_created_success) , Toast.LENGTH_LONG).show()
                    onBackPressed()
                }
                is SignupState.PasswordNotMatch -> showMessageById(R.string.password_not_match)
                is SignupState.UserExist -> showMessageById(R.string.user_exist)
                is SignupState.Fail -> showMessage(it.errorMessage)
                is SignupState.ErrorCreatingUser -> showMessageById(R.string.error_creating_user)
                is SignupState.EmptyFields -> showMessageById(R.string.error_empty_fields)
            }
        })
    }

    private fun listeners() {
        binding.btnContinue.setOnClickListener {
            tryCreateUser()
            binding.btnContinue.hideKeyboard(this)
        }
        binding.backIcon.setOnClickListener {
            onBackPressed()
        }
    }

    private fun tryCreateUser() {
        val user = binding.userEdittext.text.toString()
        val password = binding.passwordEdittext.text.toString()
        val confirmPassword = binding.passwordConfirmEdittext.text.toString()
        showProgress(true)
        enableButton(false)
        viewModel.validateUser(user, password, confirmPassword)
    }

    private fun showMessageById(message: Int) {
        Snackbar.make(binding.constraintLayout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.constraintLayout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun enableButton(value: Boolean) {
        binding.btnContinue.isEnabled = value
    }
    private fun showProgress(value: Boolean) {
        binding.progressBar.isVisible = value
    }

}