package com.studio.jozu.bloodmonitor.service.aws

import android.content.Context
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProvider
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient
import com.amazonaws.services.cognitoidentityprovider.model.CodeMismatchException
import com.amazonaws.services.cognitoidentityprovider.model.UsernameExistsException
import com.studio.jozu.bloodmonitor.di.AppComponent
import com.studio.jozu.bloodmonitor.domain.signin.SignUpConfirmCode
import com.studio.jozu.bloodmonitor.domain.signin.SignUpUser
import com.studio.jozu.bloodmonitor.event.signin.RequestConfirmCodeResultEvent
import com.studio.jozu.bloodmonitor.event.signin.SendConfirmCodeResultEvent
import com.studio.jozu.bloodmonitor.event.signin.SignUpResultEvent
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class CognitoManager {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var awsManager: AwsManager

    private lateinit var userPool: CognitoUserPool

    init {
        AppComponent.Component.component?.inject(this)
        createUserPool()
    }

    /**
     * ユーザプールの作成
     */
    private fun createUserPool() {
        val clientConfiguration = ClientConfiguration()
        val cognitoProvider: AmazonCognitoIdentityProvider = AmazonCognitoIdentityProviderClient(AnonymousAWSCredentials(), clientConfiguration)
        cognitoProvider.setRegion(awsManager.aws.regionType)

        userPool = CognitoUserPool(context, awsManager.aws.cognito.userPoolId, awsManager.aws.cognito.clientId, awsManager.aws.cognito.clientSecret, cognitoProvider)
    }

    /**
     * SignUp
     */
    fun signUpInBackground(signUpUser: SignUpUser) {
        val userAttributes = CognitoUserAttributes()

        userPool.signUpInBackground(
                signUpUser.mailAddress,
                signUpUser.password,
                userAttributes,
                null,
                object : SignUpHandler {
                    override fun onSuccess(user: CognitoUser?, signUpConfirmationState: Boolean, cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?) {
                        if (signUpConfirmationState) {
                            Timber.i("sign up complete.")
                            EventBus.getDefault().post(SignUpResultEvent(SignUpResultEvent.SignUpResult.SUCCESS, signUpUser))
                        } else {
                            Timber.w("sign up need confirm code.")
                            EventBus.getDefault().post(SignUpResultEvent(SignUpResultEvent.SignUpResult.NEED_CONFIRM, signUpUser))
                        }
                    }

                    override fun onFailure(exception: Exception?) {
                        if (exception is UsernameExistsException) {
                            EventBus.getDefault().post(SignUpResultEvent(SignUpResultEvent.SignUpResult.FAIL_USER_EXIST, signUpUser))
                            return
                        }

                        Timber.e(exception)
                        EventBus.getDefault().post(SignUpResultEvent(SignUpResultEvent.SignUpResult.FAIL, signUpUser))
                    }
                })
    }

    /**
     * 確認コードの送信
     */
    fun sendConfirmCode(signUpConfirmCode: SignUpConfirmCode) {
        userPool.getUser(signUpConfirmCode.mailAddress)
                .confirmSignUpInBackground(
                        signUpConfirmCode.confirmCode,
                        true,
                        object : GenericHandler {
                            override fun onSuccess() {
                                Timber.i("resend confirm code success.")
                                EventBus.getDefault().post(SendConfirmCodeResultEvent.SUCCESS)
                            }

                            override fun onFailure(exception: Exception?) {
                                if(exception is CodeMismatchException) {
                                    EventBus.getDefault().post(SendConfirmCodeResultEvent.CODE_MISMATCH)
                                    return
                                }

                                Timber.e(exception)
                                EventBus.getDefault().post(SendConfirmCodeResultEvent.FAIL)
                            }
                        })
    }

    /**
     * 確認コードの再送要求
     */
    fun requestConfirmCode(signUpConfirmCode: SignUpConfirmCode) {
        userPool.getUser(signUpConfirmCode.mailAddress)
                .resendConfirmationCodeInBackground(object : VerificationHandler {
                    override fun onSuccess(verificationCodeDeliveryMedium: CognitoUserCodeDeliveryDetails?) {
                        Timber.i("resend confirm code success.")
                        EventBus.getDefault().post(RequestConfirmCodeResultEvent.SUCCESS)
                    }

                    override fun onFailure(exception: Exception?) {
                        Timber.e(exception)
                        EventBus.getDefault().post(RequestConfirmCodeResultEvent.FAIL)
                    }
                })
    }
}