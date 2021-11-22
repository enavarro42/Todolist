package com.squadmarkers.todolist.ui.task.actions

import com.squadmarkers.todolist.data.models.TaskModel
import com.xwray.groupie.Item

sealed class TaskAction {
    class OnSuccess(val taskList: List<TaskModel>?, val taskItems: List<Item<*>>) : TaskAction()
}