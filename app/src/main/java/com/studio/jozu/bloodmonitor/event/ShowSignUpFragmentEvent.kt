package com.studio.jozu.bloodmonitor.event

import com.studio.jozu.bloodmonitor.domain.aws.SignInUser

data class ShowSignUpFragmentEvent(val user: SignInUser)