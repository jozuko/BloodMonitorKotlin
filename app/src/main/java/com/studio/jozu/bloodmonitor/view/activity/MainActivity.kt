package com.studio.jozu.bloodmonitor.view.activity

import android.os.Bundle
import com.studio.jozu.bloodmonitor.R
import com.studio.jozu.bloodmonitor.domain.aws.SignInUser
import com.studio.jozu.bloodmonitor.event.ShowSignInFragmentEvent
import com.studio.jozu.bloodmonitor.event.ShowSignUpFragmentEvent
import com.studio.jozu.bloodmonitor.view.fragment.SignInFragment
import com.studio.jozu.bloodmonitor.view.fragment.SignUpFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showSignUp(SignInUser())
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

    private fun showSignUp(user: SignInUser) {
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
}
