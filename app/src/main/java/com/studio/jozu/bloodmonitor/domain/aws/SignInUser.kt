package com.studio.jozu.bloodmonitor.domain.aws

import com.google.gson.annotations.SerializedName

data class SignInUser(
        @SerializedName("mail_address")
        val mailAddress: String = "",

        @SerializedName("password")
        val password: String = ""
)