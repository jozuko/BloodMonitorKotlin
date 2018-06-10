package com.studio.jozu.bloodmonitor.domain.signin

import com.google.gson.annotations.SerializedName
import com.studio.jozu.bloodmonitor.event.signin.VerifySignUpUserEvent

data class SignInUser(
        @SerializedName("mail_address")
        val mailAddress: String = "",

        @SerializedName("password")
        val password: String = ""
) {

    fun verify(): VerifySignUpUserEvent {
        if(mailAddress.isEmpty()) return VerifySignUpUserEvent.EMPTY_EMAIL
        if(password.isEmpty()) return VerifySignUpUserEvent.EMPTY_PASSWORD

        return VerifySignUpUserEvent.OK
    }
}