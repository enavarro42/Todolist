package com.squadmarkers.todolist.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squadmarkers.todolist.data.models.TaskModel
import com.squadmarkers.todolist.data.models.User
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseRepository {

    val firestore = FirebaseFirestore.getInstance()

    suspend fun createUser(user: String, password: String): Boolean {
        return try {
            firestore.collection("users")
                .document(user)
                .set(User(user, password))
                .await()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun getUser(user: String) : Task<User> {
        val data = firestore.collection("users")
            .document(user)
            .get()
        return data.continueWith {
            if (data.isSuccessful){
                return@continueWith data.result?.toObject(User::class.java)
            } else {
                return@continueWith null
            }
        }
    }

    suspend fun setTask(
        user: String,
        title: String,
        description: String,
        imageList: List<String>? = emptyList()
    ): Boolean {

        val task = TaskModel(
            title,
            description,
            imageList
        )
        return try {
            firestore.collection("task_"+user)
                .document(UUID.randomUUID().toString())
                .set(task)
                .await()
            return true
        } catch (e: Exception) {
            return false
        }

    }

    suspend fun getTask(user: String): QuerySnapshot? {
        return firestore.collection("task_"+user)
            .get()
            .await()
    }

    companion object {
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val IMAGE = "image_"
    }

}