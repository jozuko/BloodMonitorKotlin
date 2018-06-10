package com.studio.jozu.bloodmonitor.view.presenter

import com.studio.jozu.bloodmonitor.databinding.FragmentSignInBinding
import com.studio.jozu.bloodmonitor.di.AppComponent
import com.studio.jozu.bloodmonitor.domain.signin.SignInUser
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpConfirmCodeFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifySignInUserEvent
import com.studio.jozu.bloodmonitor.service.aws.CognitoManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class SignInPresenter(private val binding: FragmentSignInBinding) {

    @Inject
    lateinit var cognitoManager: CognitoManager

    init {
        AppComponent.Component.component?.inject(this)
    }

    fun onClickSignIn() {
        Timber.d("onClickSignIn")

        // データ取得
        val signInUser = SignInUser(binding.editMailAddress.text.toString(), binding.editPassword.text.toString())

        // データチェック
        val verifyResult = signInUser.verify()
        EventBus.getDefault().post(verifyResult)
        if (verifyResult != VerifySignInUserEvent.OK) return

        // 認証
        cognitoManager.signInBackground(signInUser)
    }

    fun onClickShowSignUp() {
        Timber.d("onClickShowSignUp")

        val user = SignUpUser(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignUpFragmentEvent(user))
    }

    fun onClickShowSignUpConfirmCode() {
        Timber.d("onClickShowSignUpConfirmCode")

        val signUpConfirmCode = SignUpConfirmCode(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignUpConfirmCodeFragmentEvent(signUpConfirmCode))
    }
}