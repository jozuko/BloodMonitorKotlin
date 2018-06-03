package com.studio.jozu.bloodmonitor.view.activity

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.studio.jozu.bloodmonitor.R
import org.greenrobot.eventbus.EventBus

abstract class ActivityBase : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    protected fun currentFragment(): Fragment? =
            supportFragmentManager.findFragmentById(R.id.fragment_base)

    protected fun addFragment(aFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_base, aFragment)
                .commit()
    }

    protected fun replaceFragment(aFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_base, aFragment)
                .commit()
    }
}