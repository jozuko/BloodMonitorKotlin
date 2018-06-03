package com.studio.jozu.bloodmonitor.view.fragment

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.studio.jozu.bloodmonitor.R
import com.studio.jozu.bloodmonitor.databinding.FragmentSignUpBinding
import com.studio.jozu.bloodmonitor.domain.aws.SignInUser
import com.studio.jozu.bloodmonitor.view.presenter.SignUpPresenter
import timber.log.Timber

class SignUpFragment : FragmentBase {

    companion object {
        private const val ARGS_KEY_USER = "key_user"

        fun createInstance(user: SignInUser? = SignInUser()): SignUpFragment {
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
}