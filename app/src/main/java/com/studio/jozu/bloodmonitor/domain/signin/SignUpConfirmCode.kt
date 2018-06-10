package com.studio.jozu.bloodmonitor.domain.signin

import com.google.gson.annotations.SerializedName
import com.studio.jozu.bloodmonitor.event.signin.VerifyConfirmCodeEvent

data class SignUpConfirmCode(
        @SerializedName("mail_address")
        val mailAddress: String = "",

        @SerializedName("confirm_code")
        val confirmCode: String = ""
) {

    fun verifySendCode(): VerifyConfirmCodeEvent {
        if (mailAddress.isEmpty()) return VerifyConfirmCodeEvent.EMPTY_EMAIL
        if (confirmCode.isEmpty()) return VerifyConfirmCodeEvent.EMPTY_CODE

        return VerifyConfirmCodeEvent.OK
    }

    fun verifyRequestCode(): VerifyConfirmCodeEvent {
        if (mailAddress.isEmpty()) return VerifyConfirmCodeEvent.EMPTY_EMAIL

        return VerifyConfirmCodeEvent.OK
    }
}