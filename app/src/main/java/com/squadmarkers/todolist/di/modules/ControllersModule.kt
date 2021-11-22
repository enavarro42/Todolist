package com.squadmarkers.todolist.di.modules

import com.squadmarkers.todolist.di.scope.AppScoped
import com.squadmarkers.todolist.utils.ManagePassword
import dagger.Module
import dagger.Provides

@Module
class ControllersModule {
    @Provides
    @AppScoped
    fun provideManagePassword(): ManagePassword = ManagePassword()
}