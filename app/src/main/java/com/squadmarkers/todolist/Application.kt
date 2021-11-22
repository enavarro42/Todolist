package com.squadmarkers.todolist

import com.squadmarkers.todolist.di.DaggerTodolistComponent
import com.squadmarkers.todolist.utils.Preferences
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class Application: DaggerApplication() {

    val prefs: Preferences by lazy {
        Application.preference!!
    }

    companion object {
        var preference: Preferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        preference = Preferences(applicationContext)
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTodolistComponent.builder().application(this).build()
    }
}