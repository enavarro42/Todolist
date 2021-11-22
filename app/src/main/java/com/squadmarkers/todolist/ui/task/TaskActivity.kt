package com.squadmarkers.todolist.ui.task

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.squadmarkers.todolist.Application
import com.squadmarkers.todolist.R
import com.squadmarkers.todolist.databinding.ActivityTaskBinding
import com.squadmarkers.todolist.di.scope.ActivityScoped
import com.squadmarkers.todolist.ui.task.actions.TaskAction
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.Section
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

@ActivityScoped
class TaskActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityTaskBinding

    private lateinit var viewModel: TaskViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val groupAdapter: GroupAdapter<GroupieViewHolder> by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listener()
        setupViewModel()
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTask()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel::class.java)
        viewModel.getStateTask().observe(this, Observer {
            when(it) {
                is TaskAction.OnSuccess -> {
                    groupAdapter.clear()
                    groupAdapter.add(
                        Section()
                            .apply {
                                addAll(it.taskItems)
                            }
                    )
                }
            }
        })
    }

    private fun listener() {
        binding.fab.setOnClickListener {
            val intent = Intent(this, SetTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
            adapter = groupAdapter
        }
    }
}