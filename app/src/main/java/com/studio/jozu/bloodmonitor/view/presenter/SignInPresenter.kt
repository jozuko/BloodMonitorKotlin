package com.studio.jozu.bloodmonitor.view.presenter

import com.studio.jozu.bloodmonitor.databinding.FragmentSignInBinding
import com.studio.jozu.bloodmonitor.domain.aws.SignInUser
import com.studio.jozu.bloodmonitor.event.ShowSignUpFragmentEvent
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class SignInPresenter(private val binding: FragmentSignInBinding) {

    fun onClickSignIn() {
        Timber.d("onClickSignIn")

    }

    fun onClickShowSignUp() {
        Timber.d("onClickShowSignUp")

        val user = SignInUser(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignUpFragmentEvent(user))
    }
}