package com.studio.jozu.bloodmonitor.view.presenter

import com.studio.jozu.bloodmonitor.databinding.FragmentSignUpBinding
import com.studio.jozu.bloodmonitor.domain.aws.SignInUser
import com.studio.jozu.bloodmonitor.event.ShowSignInFragmentEvent
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

class SignUpPresenter(private val binding: FragmentSignUpBinding) {

    fun onClickSignUp() {
        Timber.d("onClickSignUp")
    }

    fun onClickShowSignIn() {
        Timber.d("onClickShowSignIn")

        val user = SignInUser(binding.editMailAddress.text.toString())
        EventBus.getDefault().post(ShowSignInFragmentEvent(user))
    }
}