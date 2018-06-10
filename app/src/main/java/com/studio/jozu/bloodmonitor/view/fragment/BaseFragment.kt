package com.studio.jozu.bloodmonitor.view.fragment

import android.annotation.SuppressLint
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast


abstract class BaseFragment : Fragment {
    @Suppress("ConvertSecondaryConstructorToPrimary")
    @SuppressLint("ValidFragment")
    protected constructor() : super()

    protected fun showToast(@StringRes resourceId: Int) {
        Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show()
    }
}