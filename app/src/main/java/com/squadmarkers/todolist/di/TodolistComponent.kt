package com.squadmarkers.todolist.di

import com.squadmarkers.todolist.Application
import com.squadmarkers.todolist.di.modules.ActivityBuilderModule
import com.squadmarkers.todolist.di.modules.ApplicationModule
import com.squadmarkers.todolist.di.modules.ControllersModule
import com.squadmarkers.todolist.di.modules.RepositoriesModule
import com.squadmarkers.todolist.di.modules.ViewModelModule
import com.squadmarkers.todolist.di.scope.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [
    ApplicationModule::class,
    ActivityBuilderModule::class,
    AndroidSupportInjectionModule::class,
    RepositoriesModule::class,
    ViewModelModule::class,
    ControllersModule::class
])
interface TodolistComponent : AndroidInjector<Application> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TodolistComponent
    }

}