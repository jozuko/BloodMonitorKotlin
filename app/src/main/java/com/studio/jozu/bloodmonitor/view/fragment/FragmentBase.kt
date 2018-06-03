package com.studio.jozu.bloodmonitor.view.fragment

import android.annotation.SuppressLint
import android.support.v4.app.Fragment


abstract class FragmentBase : Fragment {
    @Suppress("ConvertSecondaryConstructorToPrimary")
    @SuppressLint("ValidFragment")
    protected constructor() : super()
}