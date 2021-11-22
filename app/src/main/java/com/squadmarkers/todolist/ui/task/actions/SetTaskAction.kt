package com.squadmarkers.todolist.ui.task.actions

sealed class SetTaskAction {
    object OnSuccess : SetTaskAction()
    object OnError : SetTaskAction()
    object OnEmptyFields : SetTaskAction()
}