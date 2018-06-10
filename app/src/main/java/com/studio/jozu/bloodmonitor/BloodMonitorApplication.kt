package com.studio.jozu.bloodmonitor

import android.app.Application
import com.studio.jozu.bloodmonitor.di.AppComponent
import com.studio.jozu.bloodmonitor.service.aws.AwsManager
import com.studio.jozu.bloodmonitor.service.log.DebugLogTree
import com.studio.jozu.bloodmonitor.service.log.InfoLogTree
import timber.log.Timber

@Suppress("unused")
class BloodMonitorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugLogTree())
        } else {
            Timber.plant(InfoLogTree())
        }

        AppComponent.Component.initComponent(this)
    }
}