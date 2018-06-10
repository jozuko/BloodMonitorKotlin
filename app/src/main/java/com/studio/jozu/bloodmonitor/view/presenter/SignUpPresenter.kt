package com.studio.jozu.bloodmonitor.view.presenter

import com.studio.jozu.bloodmonitor.databinding.FragmentSignUpBinding
import com.studio.jozu.bloodmonitor.di.AppComponent
import com.studio.jozu.bloodmonitor.domain.signin.SignInUser
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.ShowSignInFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpConfirmCodeFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifySignUpUserEvent
import com.studio.jozu.bloodmonitor.service.aws.CognitoManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class SignUpPresenter(private val binding: FragmentSignUpBinding) {

    @Inject
    lateinit var cognitoManager: CognitoManager

    init {
        AppComponent.Component.component?.inject(this)
    }

    fun onClickSignUp() {
        Timber.d("onClickSignUp")

        // 入力情報を取得
        val signUpUser = SignUpUser(binding.editMailAddress.text.toString(), binding.editPassword.text.toString(), binding.editPasswordConfirm.text.toString())

        // 入力チェック
        val verifyResult = signUpUser.verify()
        EventBus.getDefault().post(verifyResult)
        if (verifyResult != VerifySignUpUserEvent.OK) return

        // 認証開始
        cognitoManager.signUpInBackground(signUpUser)
    }

    fun onClickShowSignIn() {
        Timber.d("onClickShowSignIn")

        val user = SignInUser(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignInFragmentEvent(user))
    }

    fun onClickShowSignUpConfirmCode() {
        Timber.d("onClickShowSignUpConfirmCode")

        val signUpConfirmCode = SignUpConfirmCode(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignUpConfirmCodeFragmentEvent(signUpConfirmCode))
    }
}