package com.studio.jozu.bloodmonitor.domain.signin

import com.google.gson.annotations.SerializedName
import com.studio.jozu.bloodmonitor.event.signin.VerifySignUpUserEvent

data class SignUpUser(
        @SerializedName("mail_address")
        val mailAddress: String = "",

        @SerializedName("password")
        val password: String = "",

        @SerializedName("confirm_password")
        val confirmPassword: String = ""
) {

    fun verify(): VerifySignUpUserEvent {
        if (mailAddress.isEmpty()) return VerifySignUpUserEvent.EMPTY_EMAIL
        if (password.isEmpty()) return VerifySignUpUserEvent.EMPTY_PASSWORD
        if (confirmPassword.isEmpty()) return VerifySignUpUserEvent.EMPTY_PASSWORD
        if (password != confirmPassword) return VerifySignUpUserEvent.DIFFERENT_PASSWORD

        return VerifySignUpUserEvent.OK
    }
}