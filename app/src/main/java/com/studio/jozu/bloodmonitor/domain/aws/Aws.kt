package com.studio.jozu.bloodmonitor.domain.aws

import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.google.gson.annotations.SerializedName

data class Aws(
        @SerializedName("region")
        val region: String,

        @SerializedName("cognito")
        val cognito: Cognito,

        @SerializedName("s3")
        val s3: S3
) {

    val regionType: Region
        get() {
            val region = when (region) {
                "us-east-1" -> Regions.US_EAST_1
                "us-east-2" -> Regions.US_EAST_2
                "us-west-1" -> Regions.US_WEST_1
                "us-west-2" -> Regions.US_WEST_2
                "eu-west-1" -> Regions.EU_WEST_1
                "eu-west-2" -> Regions.EU_WEST_2
                "eu-central-1" -> Regions.EU_CENTRAL_1
                "ap-southeast-1" -> Regions.AP_SOUTHEAST_1
                "ap-southeast-2" -> Regions.AP_SOUTHEAST_2
                "ap-northeast-1" -> Regions.AP_NORTHEAST_1
                "ap-northeast-2" -> Regions.AP_NORTHEAST_2
                "ap-south-1" -> Regions.AP_SOUTH_1
                "sa-east-1" -> Regions.SA_EAST_1
                "cn-north-1" -> Regions.CN_NORTH_1
                "ca-central-1" -> Regions.CA_CENTRAL_1
                else -> Regions.DEFAULT_REGION
            }
            return Region.getRegion(region)
        }
}