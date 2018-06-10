package com.studio.jozu.bloodmonitor.di

import com.studio.jozu.bloodmonitor.BloodMonitorApplication
import com.studio.jozu.bloodmonitor.service.aws.AwsManager
import com.studio.jozu.bloodmonitor.service.aws.CognitoManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: BloodMonitorApplication) {

    @Singleton
    @Provides
    fun provideContext() = application.applicationContext

    @Singleton
    @Provides
    fun provideAwsManager() = AwsManager(application.applicationContext)

    @Singleton
    @Provides
    fun provideCognitoManager() = CognitoManager()
}