package com.studio.jozu.bloodmonitor.view.fragment

import org.greenrobot.eventbus.EventBus


abstract class BaseFragmentEventBus : BaseFragment() {
    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }
}