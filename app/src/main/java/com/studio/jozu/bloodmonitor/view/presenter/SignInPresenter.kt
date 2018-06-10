package com.studio.jozu.bloodmonitor.view.presenter

import com.studio.jozu.bloodmonitor.databinding.FragmentSignInBinding
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpConfirmCodeFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpFragmentEvent
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class SignInPresenter(private val binding: FragmentSignInBinding) {

    fun onClickSignIn() {
        Timber.d("onClickSignIn")

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