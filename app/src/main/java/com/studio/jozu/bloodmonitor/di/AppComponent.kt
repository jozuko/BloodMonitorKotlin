package com.studio.jozu.bloodmonitor.di

import com.studio.jozu.bloodmonitor.BloodMonitorApplication
import com.studio.jozu.bloodmonitor.service.aws.CognitoManager
import com.studio.jozu.bloodmonitor.view.presenter.SignUpConfirmCodePresenter
import com.studio.jozu.bloodmonitor.view.presenter.SignUpPresenter
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = [AppModule::class])
interface AppComponent {
    object Component {
        var component: AppComponent? = null
        fun initComponent(application: BloodMonitorApplication) {
            if (component != null) return

            component = DaggerAppComponent.builder()
                    .appModule(AppModule(application))
                    .build()
        }
    }

    fun inject(cognitoManager: CognitoManager)

    fun inject(singUpPresenter: SignUpPresenter)
    fun inject(singUpConfirmCodePresenter: SignUpConfirmCodePresenter)
}