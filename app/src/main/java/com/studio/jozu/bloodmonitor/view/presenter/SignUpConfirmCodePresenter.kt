package com.studio.jozu.bloodmonitor.view.presenter

import com.studio.jozu.bloodmonitor.databinding.FragmentSignUpConfirmCodeBinding
import com.studio.jozu.bloodmonitor.di.AppComponent
import com.studio.jozu.bloodmonitor.domain.signin.SignInUser
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.ShowSignInFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifyConfirmCodeEvent
import com.studio.jozu.bloodmonitor.service.aws.CognitoManager
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

class SignUpConfirmCodePresenter(private val binding: FragmentSignUpConfirmCodeBinding) {

    @Inject
    lateinit var cognitoManager: CognitoManager

    init {
        AppComponent.Component.component?.inject(this)
    }

    fun onClickSendConfirmCode() {
        Timber.d("onClickSendConfirmCode")

        // データ取得
        val signUpConfirmCode = getSignUpConfirmCodeFromDisplay()

        // データチェック
        val verifySendResult = signUpConfirmCode.verifySendCode()
        EventBus.getDefault().post(verifySendResult)
        if(verifySendResult != VerifyConfirmCodeEvent.OK) return

        // 確認コード再送要求
        cognitoManager.sendConfirmCode(signUpConfirmCode)
    }

    fun onClickRequestConfirmCode() {
        Timber.d("onClickRequestConfirmCode")

        // データ取得
        val signUpConfirmCode = getSignUpConfirmCodeFromDisplay()

        // データチェック
        val verifyRequestResult = signUpConfirmCode.verifyRequestCode()
        EventBus.getDefault().post(verifyRequestResult)
        if(verifyRequestResult != VerifyConfirmCodeEvent.OK) return

        // 確認コード再送要求
        cognitoManager.requestConfirmCode(signUpConfirmCode)
    }

    private fun getSignUpConfirmCodeFromDisplay(): SignUpConfirmCode {
        return SignUpConfirmCode(
                binding.editMailAddress.text.toString(),
                binding.editConfirmCode.text.toString())
    }

    fun onClickShowSignIn() {
        Timber.d("onClickShowSignIn")

        val user = SignInUser(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignInFragmentEvent(user))
    }

    fun onClickShowSignUp() {
        Timber.d("onClickShowSignUp")

        val user = SignUpUser(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignUpFragmentEvent(user))
    }


}