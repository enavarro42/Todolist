package com.squadmarkers.todolist.utils

import com.squadmarkers.todolist.data.models.TaskModel
import com.squadmarkers.todolist.ui.common.views.TaskView
import com.xwray.groupie.Item

object TaskMapper {

    fun taskMapper(list: List<TaskModel>) : List<Item<*>> {
        return list.map {
            TaskView(it)
        }
    }
}