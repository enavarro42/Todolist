package com.squadmarkers.todolist.di.modules

import com.squadmarkers.todolist.data.FirebaseRepository
import com.squadmarkers.todolist.di.scope.AppScoped
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {
    @Provides
    @AppScoped
    fun provideFirebaseRepository(): FirebaseRepository = FirebaseRepository()
}