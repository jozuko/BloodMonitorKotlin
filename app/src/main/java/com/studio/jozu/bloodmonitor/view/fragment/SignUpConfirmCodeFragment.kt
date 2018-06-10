package com.studio.jozu.bloodmonitor.view.fragment

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.studio.jozu.bloodmonitor.R
import com.studio.jozu.bloodmonitor.databinding.FragmentSignUpConfirmCodeBinding
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.event.signin.RequestConfirmCodeResultEvent
import com.studio.jozu.bloodmonitor.event.signin.SendConfirmCodeResultEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifyConfirmCodeEvent
import com.studio.jozu.bloodmonitor.view.presenter.SignUpConfirmCodePresenter
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber

class SignUpConfirmCodeFragment : BaseFragmentEventBus {

    companion object {
        private const val ARGS_KEY_CONFIRM_CODE = "key_user"

        fun createInstance(user: SignUpConfirmCode? = SignUpConfirmCode()): SignUpConfirmCodeFragment {
            val fragment = SignUpConfirmCodeFragment()

            val args = Bundle()
            user?.let { args.putString(ARGS_KEY_CONFIRM_CODE, Gson().toJson(it)) }
            fragment.arguments = args

            return fragment
        }
    }

    private lateinit var binding: FragmentSignUpConfirmCodeBinding

    @Suppress("ConvertSecondaryConstructorToPrimary")
    @SuppressLint("ValidFragment")
    private constructor() : super()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_confirm_code, container, false)
        binding.presenter = SignUpConfirmCodePresenter(binding)
        binding.signUpConfirmCode = getArgsSignUpConfirmCode()

        binding.editConfirmCode.requestFocus()

        return binding.root
    }

    private fun getArgsSignUpConfirmCode(): SignUpConfirmCode {
        val userJsonString = arguments?.getString(ARGS_KEY_CONFIRM_CODE) ?: ""
        val user = if (userJsonString.isNotEmpty()) {
            Gson().fromJson(userJsonString, SignUpConfirmCode::class.java)
        } else {
            SignUpConfirmCode()
        }

        Timber.d("args user = $user")
        return user
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onVerifyConfirmCodeEvent(event: VerifyConfirmCodeEvent) {
        when (event) {
            VerifyConfirmCodeEvent.EMPTY_EMAIL -> {
                showToast(R.string.message_empty_email)
            }
            VerifyConfirmCodeEvent.EMPTY_CODE -> {
                showToast(R.string.message_empty_confirm_code)
            }
            else -> {
                // do nothing.
            }
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRequestConfirmCodeResultEvent(event: RequestConfirmCodeResultEvent) {
        when(event) {
            RequestConfirmCodeResultEvent.SUCCESS -> {
                showToast(R.string.message_request_confirm_success)
                binding.editConfirmCode.requestFocus()
            }
            else -> {
                showToast(R.string.message_request_confirm_fail)
            }
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSendConfirmCodeResultEvent(event: SendConfirmCodeResultEvent) {
        when(event){
            SendConfirmCodeResultEvent.SUCCESS -> {
                Timber.i("send confirm success.")
            }
            SendConfirmCodeResultEvent.CODE_MISMATCH -> {
                showToast(R.string.message_send_confirm_code_mismatch_fail)
            }
            else -> {
                showToast(R.string.message_send_confirm_fail)
            }
        }
    }
}