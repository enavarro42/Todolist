package com.squadmarkers.todolist.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squadmarkers.todolist.di.ViewModelKey
import com.squadmarkers.todolist.di.scope.AppScoped
import com.squadmarkers.todolist.ui.common.viewmodel.ViewModelFactory
import com.squadmarkers.todolist.ui.login.LoginViewModel
import com.squadmarkers.todolist.ui.signup.SignupViewModel
import com.squadmarkers.todolist.ui.task.SetTaskViewModel
import com.squadmarkers.todolist.ui.task.TaskViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @AppScoped
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    abstract fun bindRegisterViewModel(viewModel: SignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetTaskViewModel::class)
    abstract fun bindSetTaskViewModel(viewModel: SetTaskViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    abstract fun bindTaskViewModel(viewModel: TaskViewModel): ViewModel

}