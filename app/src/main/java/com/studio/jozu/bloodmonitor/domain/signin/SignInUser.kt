package com.studio.jozu.bloodmonitor.domain.signin

import com.google.gson.annotations.SerializedName
import com.studio.jozu.bloodmonitor.event.signin.VerifySignInUserEvent
import com.studio.jozu.bloodmonitor.event.signin.VerifySignUpUserEvent

data class SignInUser(
        @SerializedName("mail_address")
        val mailAddress: String = "",

        @SerializedName("password")
        val password: String = ""
) {

    fun verify(): VerifySignInUserEvent {
        if(mailAddress.isEmpty()) return VerifySignInUserEvent.EMPTY_EMAIL
        if(password.isEmpty()) return VerifySignInUserEvent.EMPTY_PASSWORD

        return VerifySignInUserEvent.OK
    }
}