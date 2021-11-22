package com.squadmarkers.todolist.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.squadmarkers.todolist.R
import com.squadmarkers.todolist.databinding.ActivityLoginBinding
import com.squadmarkers.todolist.di.scope.ActivityScoped
import com.squadmarkers.todolist.ui.signup.SignupActivity
import com.squadmarkers.todolist.ui.signup.SignupViewModel
import com.squadmarkers.todolist.ui.task.TaskActivity
import com.squadmarkers.todolist.utils.ManagePassword
import com.squadmarkers.todolist.utils.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@ActivityScoped
class LoginActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var managePassword: ManagePassword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupViewModel()
        listener()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        viewModel.getStateLogin().observe(this, Observer {
            enableButton(true)
            showProgress(false)
            when (it) {
                is LoginState.OnSuccess -> goToTaskActivity()
                is LoginState.EmptyFields -> showMessageById(R.string.error_empty_fields)
                is LoginState.InvalidLogin -> binding.errorMessage.visibility = View.VISIBLE
            }
        })
    }

    private fun showMessageById(message: Int) {
        Snackbar.make(binding.constraintLayout, message, Snackbar.LENGTH_LONG).show()
    }

    private fun listener() {
        binding.contentSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.btnContinue.setOnClickListener {
            binding.btnContinue.hideKeyboard(this)
            tryLogin(binding.userEdittext.text.toString(), binding.passwordEdittext.text.toString())
        }

        binding.userEdittext.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) = Unit

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                binding.errorMessage.visibility = View.INVISIBLE
            }
        })

        binding.passwordEdittext.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) = Unit

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                binding.errorMessage.visibility = View.INVISIBLE
            }
        })
    }

    private fun tryLogin(user: String, password: String) {
        showProgress(true)
        enableButton(false)
        viewModel.login(this, user, password)
    }

    private fun goToTaskActivity() {
        val intent = Intent(this, TaskActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun enableButton(value: Boolean) {
        binding.btnContinue.isEnabled = value
    }
    private fun showProgress(value: Boolean) {
        binding.progressBar.isVisible = value
    }
}