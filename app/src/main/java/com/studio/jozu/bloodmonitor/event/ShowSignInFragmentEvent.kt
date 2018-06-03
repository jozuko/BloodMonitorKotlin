package com.studio.jozu.bloodmonitor.event

import com.studio.jozu.bloodmonitor.domain.aws.SignInUser

data class ShowSignInFragmentEvent(val user: SignInUser)
