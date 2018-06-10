package com.studio.jozu.bloodmonitor.view.fragment

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.studio.jozu.bloodmonitor.R
import com.studio.jozu.bloodmonitor.databinding.FragmentSignInBinding
import com.studio.jozu.bloodmonitor.domain.signin.SignInUser
import com.studio.jozu.bloodmonitor.event.signin.SignInResultEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifySignInUserEvent
import com.studio.jozu.bloodmonitor.view.presenter.SignInPresenter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class SignInFragment : BaseFragmentEventBus {

    companion object {
        private const val ARGS_KEY_USER = "key_user"

        fun createInstance(user: SignInUser? = SignInUser()): SignInFragment {
            val fragment = SignInFragment()

            val args = Bundle()
            user?.let { args.putString(ARGS_KEY_USER, Gson().toJson(it)) }
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentSignInBinding

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @SuppressLint("ValidFragment")
    private constructor() : super()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.presenter = SignInPresenter(binding)
        binding.user = getArgsUser()

        return binding.root
    }

    private fun getArgsUser(): SignInUser {
        val userJsonString = arguments?.getString(ARGS_KEY_USER) ?: ""
        val user = if (userJsonString.isNotEmpty()) {
            Gson().fromJson(userJsonString, SignInUser::class.java)
        } else {
            SignInUser()
        }

        Timber.d("args user = $user")
        return user
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVerifySignInUserEvent(event: VerifySignInUserEvent) {
        when(event){
            VerifySignInUserEvent.EMPTY_EMAIL -> showToast(R.string.message_empty_email)
            VerifySignInUserEvent.EMPTY_PASSWORD -> showToast(R.string.message_empty_password)
            else -> {
                // do nothing.
            }
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSignInResultEvent(event: SignInResultEvent) {
        when(event) {
            SignInResultEvent.SUCCESS -> Timber.i("login success.")
            else -> showToast(R.string.message_sign_in_fail)
        }
    }
}