package com.squadmarkers.todolist.ui.task

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.squadmarkers.todolist.Application
import com.squadmarkers.todolist.R
import com.squadmarkers.todolist.databinding.ActivitySetTaskBinding
import com.squadmarkers.todolist.di.scope.ActivityScoped
import com.squadmarkers.todolist.ui.signup.SignupViewModel
import com.squadmarkers.todolist.ui.task.actions.SetTaskAction
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@ActivityScoped
class SetTaskActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivitySetTaskBinding

    private lateinit var viewModel: SetTaskViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        listener()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SetTaskViewModel::class.java)
        viewModel.getStateTask().observe(this, Observer {
            when(it) {
                is SetTaskAction.OnSuccess -> finish()
                is SetTaskAction.OnEmptyFields -> binding.errorMessage.visibility = View.VISIBLE
            }
        })
    }

    private fun listener() {
        binding.btnContinue.setOnClickListener {
            setTask()
        }

        binding.taskName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) = Unit

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                binding.errorMessage.visibility = View.INVISIBLE
            }
        })

        binding.taskDescription.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) = Unit

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                binding.errorMessage.visibility = View.INVISIBLE
            }
        })
    }

    private fun setTask() {
        viewModel.setTask(binding.taskName.text.toString(), binding.taskDescription.text.toString(), listOf("image1", "image2"))
    }
}