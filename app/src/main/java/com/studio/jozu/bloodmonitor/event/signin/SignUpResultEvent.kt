package com.studio.jozu.bloodmonitor.event.signin

import com.google.gson.annotations.SerializedName
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser

data class SignUpResultEvent(
        @SerializedName("result")
        val result: SignUpResult,

        @SerializedName("user")
        val user: SignUpUser
) {
    enum class SignUpResult {
        SUCCESS,
        NEED_CONFIRM,
        FAIL_USER_EXIST,
        FAIL
    }
}
