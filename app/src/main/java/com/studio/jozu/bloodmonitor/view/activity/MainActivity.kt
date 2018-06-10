package com.studio.jozu.bloodmonitor.view.activity

import android.os.Bundle
import com.studio.jozu.bloodmonitor.R
import com.studio.jozu.bloodmonitor.domain.signin.SignInUser
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.ShowSignInFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpConfirmCodeFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpFragmentEvent
import com.studio.jozu.bloodmonitor.view.fragment.SignInFragment
import com.studio.jozu.bloodmonitor.view.fragment.SignUpConfirmCodeFragment
import com.studio.jozu.bloodmonitor.view.fragment.SignUpFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivityEventBus() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showSignUp(SignUpUser())
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShowSignInFragmentEvent(event: ShowSignInFragmentEvent) {
        showSignIn(event.user)
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShowSignUpFragmentEvent(event: ShowSignUpFragmentEvent) {
        showSignUp(event.user)
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShowSignUpConfirmCodeFragmentEvent(event: ShowSignUpConfirmCodeFragmentEvent) {
        showSignUpConfirmCode(event.signUpConfirmCode)
    }

    private fun showSignUp(user: SignUpUser) {
        val signUpFragment = SignUpFragment.createInstance(user)
        val currentFragment = currentFragment()

        if (currentFragment == null) {
            addFragment(signUpFragment)
            return
        }

        if (currentFragment is SignUpFragment) return
        replaceFragment(signUpFragment)
    }

    private fun showSignIn(user: SignInUser) {
        val singInFragment = SignInFragment.createInstance(user)
        val currentFragment = currentFragment()

        if (currentFragment == null) {
            addFragment(singInFragment)
            return
        }

        if (currentFragment is SignInFragment) return
        replaceFragment(singInFragment)
    }

    private fun showSignUpConfirmCode(signUpConfirmCode: SignUpConfirmCode) {
        val signUpConfirmCodeFragment = SignUpConfirmCodeFragment.createInstance(signUpConfirmCode)
        val currentFragment = currentFragment()

        if (currentFragment == null) {
            addFragment(signUpConfirmCodeFragment)
            return
        }
        if (currentFragment is SignUpConfirmCodeFragment) {
            return
        }
        replaceFragment(signUpConfirmCodeFragment)
    }
}
