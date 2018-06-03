package com.studio.jozu.bloodmonitor.domain.aws

import com.google.gson.annotations.SerializedName

data class Cognito(
        @SerializedName("user_pool_id")
        val userPoolId: String,

        @SerializedName("identity_pool_id")
        val identityPoolId: String,

        @SerializedName("client_id")
        val clientId: String,

        @SerializedName("client_secret")
        val clientSecret: String
)