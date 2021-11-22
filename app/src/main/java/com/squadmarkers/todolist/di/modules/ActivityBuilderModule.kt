package com.squadmarkers.todolist.di.modules

import com.squadmarkers.todolist.di.scope.ActivityScoped
import com.squadmarkers.todolist.ui.login.LoginActivity
import com.squadmarkers.todolist.ui.signup.SignupActivity
import com.squadmarkers.todolist.ui.task.SetTaskActivity
import com.squadmarkers.todolist.ui.task.TaskActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun loginActivity(): LoginActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun registerActivity(): SignupActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun setTaskActivity(): SetTaskActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun taskActivity(): TaskActivity

}