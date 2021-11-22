package com.squadmarkers.todolist.data.models

data class TaskModel(
    val title: String = "",
    val description: String = "",
    val imageList: List<String>? = emptyList()
)