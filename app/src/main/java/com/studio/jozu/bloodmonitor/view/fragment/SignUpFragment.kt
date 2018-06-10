package com.studio.jozu.bloodmonitor.view.fragment

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.studio.jozu.bloodmonitor.R
import com.studio.jozu.bloodmonitor.databinding.FragmentSignUpBinding
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.ShowSignUpConfirmCodeFragmentEvent
import com.studio.jozu.bloodmonitor.event.signin.SignUpResultEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifySignUpUserEvent
import com.studio.jozu.bloodmonitor.view.presenter.SignUpPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class SignUpFragment : BaseFragmentEventBus {

    companion object {
        private const val ARGS_KEY_USER = "key_user"

        fun createInstance(user: SignUpUser? = SignUpUser()): SignUpFragment {
            val fragment = SignUpFragment()

            val args = Bundle()
            user?.let { args.putString(ARGS_KEY_USER, Gson().toJson(it)) }
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentSignUpBinding

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @SuppressLint("ValidFragment")
    private constructor() : super()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.presenter = SignUpPresenter(binding)
        binding.user = getArgsUser()

        return binding.root
    }

    private fun getArgsUser(): SignUpUser {
        val userJsonString = arguments?.getString(ARGS_KEY_USER) ?: ""
        val user = if (userJsonString.isNotEmpty()) {
            Gson().fromJson(userJsonString, SignUpUser::class.java)
        } else {
            SignUpUser()
        }

        Timber.d("args user = $user")
        return user
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVerifySignUpUserEvent(event: VerifySignUpUserEvent) {
        when (event) {
            VerifySignUpUserEvent.EMPTY_EMAIL -> {
                Toast.makeText(context, R.string.message_empty_email, Toast.LENGTH_SHORT).show()
            }
            VerifySignUpUserEvent.EMPTY_PASSWORD -> {
                Toast.makeText(context, R.string.message_empty_password, Toast.LENGTH_SHORT).show()
            }
            VerifySignUpUserEvent.DIFFERENT_PASSWORD -> {
                Toast.makeText(context, R.string.message_different_password, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // do nothing.
            }
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSignUpResultEvent(event: SignUpResultEvent) {
        when (event.result) {
            SignUpResultEvent.SignUpResult.FAIL -> {
                Toast.makeText(context, R.string.message_sign_up_fail, Toast.LENGTH_SHORT).show()
            }
            SignUpResultEvent.SignUpResult.NEED_CONFIRM -> {
                showConfirmCodeFragment(event.user)
            }
            SignUpResultEvent.SignUpResult.FAIL_USER_EXIST -> {
                Toast.makeText(context, R.string.message_sign_up_fail_user_exist, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // do nothing.
            }
        }
    }

    private fun showConfirmCodeFragment(singUpUser: SignUpUser) {
        val signUpConfirmCode = SignUpConfirmCode(singUpUser.mailAddress)
        EventBus.getDefault().post(ShowSignUpConfirmCodeFragmentEvent(signUpConfirmCode))
    }
}