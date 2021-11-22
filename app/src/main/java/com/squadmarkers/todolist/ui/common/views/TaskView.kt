package com.squadmarkers.todolist.ui.common.views

import android.view.View
import com.squadmarkers.todolist.R
import com.squadmarkers.todolist.data.models.TaskModel
import com.squadmarkers.todolist.databinding.TaskLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class TaskView(
    val task: TaskModel
) : BindableItem<TaskLayoutBinding>() {

    private lateinit var binding: TaskLayoutBinding

    override fun bind(viewBinding: TaskLayoutBinding, position: Int) {
        this.binding = viewBinding
        binding.title.text = task.title.orEmpty()
    }

    override fun getLayout(): Int = R.layout.task_layout

    override fun initializeViewBinding(view: View): TaskLayoutBinding {
        return TaskLayoutBinding.bind(view)
    }

}