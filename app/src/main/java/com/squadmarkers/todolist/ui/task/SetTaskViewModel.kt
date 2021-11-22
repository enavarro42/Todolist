package com.squadmarkers.todolist.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squadmarkers.todolist.Application
import com.squadmarkers.todolist.Application.Companion.preference
import com.squadmarkers.todolist.data.FirebaseRepository
import com.squadmarkers.todolist.ui.task.actions.SetTaskAction
import com.squadmarkers.todolist.ui.task.actions.TaskAction
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SetTaskViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val networkScope = CoroutineScope(Dispatchers.IO)

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val stateTask = MutableLiveData<SetTaskAction>()

    fun getStateTask() : LiveData<SetTaskAction> = stateTask

    fun setTask(title: String, description: String, imageList: List<String>) {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            networkScope.launch(errorHandler) {
                preference?.currentUser?.let {
                    if (firebaseRepository.setTask(it, title, description, imageList)) {
                        stateTask.postValue(SetTaskAction.OnSuccess)
                    } else {
                        stateTask.postValue(SetTaskAction.OnError)
                    }
                }
            }
        } else {
            stateTask.value = SetTaskAction.OnEmptyFields
        }
    }

}