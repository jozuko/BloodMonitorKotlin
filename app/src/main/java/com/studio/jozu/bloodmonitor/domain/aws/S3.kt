package com.studio.jozu.bloodmonitor.domain.aws

import com.google.gson.annotations.SerializedName

data class S3(
        @SerializedName("bucket")
        val bucket: String)