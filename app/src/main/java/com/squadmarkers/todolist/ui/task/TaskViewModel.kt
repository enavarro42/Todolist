package com.squadmarkers.todolist.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squadmarkers.todolist.Application
import com.squadmarkers.todolist.data.FirebaseRepository
import com.squadmarkers.todolist.data.models.TaskModel
import com.squadmarkers.todolist.ui.signup.SignupState
import com.squadmarkers.todolist.ui.task.actions.TaskAction
import com.squadmarkers.todolist.utils.TaskMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val networkScope = CoroutineScope(Dispatchers.IO)

    val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
    }

    private val stateTask = MutableLiveData<TaskAction>()

    fun getStateTask() : LiveData<TaskAction> = stateTask

    fun getTask() {
        networkScope.launch(errorHandler) {
            val querySnapShot = firebaseRepository.getTask(Application.preference?.currentUser.orEmpty())
            querySnapShot?.let {
                val dataList : List<TaskModel> = it.toObjects(TaskModel::class.java)
                stateTask.postValue(TaskAction.OnSuccess(dataList, TaskMapper.taskMapper(dataList)))
            }

        }
    }

}