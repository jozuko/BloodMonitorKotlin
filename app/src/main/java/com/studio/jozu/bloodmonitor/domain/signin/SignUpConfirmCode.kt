package com.studio.jozu.bloodmonitor.domain.signin

import com.google.gson.annotations.SerializedName
import com.studio.jozu.bloodmonitor.event.signin.VerifySignUpConfirmCodeEvent

data class SignUpConfirmCode(
        @SerializedName("mail_address")
        val mailAddress: String = "",

        @SerializedName("confirm_code")
        val confirmCode: String = ""
) {

    fun verifySendCode(): VerifySignUpConfirmCodeEvent {
        if (mailAddress.isEmpty()) return VerifySignUpConfirmCodeEvent.EMPTY_EMAIL
        if (confirmCode.isEmpty()) return VerifySignUpConfirmCodeEvent.EMPTY_CODE

        return VerifySignUpConfirmCodeEvent.OK
    }

    fun verifyRequestCode(): VerifySignUpConfirmCodeEvent {
        if (mailAddress.isEmpty()) return VerifySignUpConfirmCodeEvent.EMPTY_EMAIL

        return VerifySignUpConfirmCodeEvent.OK
    }
}