package com.studio.jozu.bloodmonitor.view.activity

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.studio.jozu.bloodmonitor.R
import org.greenrobot.eventbus.EventBus

abstract class BaseActivityEventBus : BaseActivity() {

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}