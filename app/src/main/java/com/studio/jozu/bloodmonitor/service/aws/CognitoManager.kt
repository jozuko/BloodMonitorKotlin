package com.studio.jozu.bloodmonitor.service.aws

import android.annotation.SuppressLint
import android.content.Context
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProvider
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient
import com.studio.jozu.bloodmonitor.domain.aws.Aws

object CognitoManager {
    private lateinit var aws: Aws

    @SuppressLint("StaticFieldLeak")
    lateinit var userPool: CognitoUserPool

    fun setup(aContext: Context, aAws: Aws) {
        aws = aAws
        createUserPool(aContext = aContext)
    }

    private fun createUserPool(aContext: Context) {
        val clientConfiguration = ClientConfiguration()
        val cognitoProvider: AmazonCognitoIdentityProvider = AmazonCognitoIdentityProviderClient(AnonymousAWSCredentials(), clientConfiguration)
        cognitoProvider.setRegion(aws.regionType)

        userPool = CognitoUserPool(aContext, aws.cognito.userPoolId, aws.cognito.clientId, aws.cognito.clientSecret, cognitoProvider)
    }
}