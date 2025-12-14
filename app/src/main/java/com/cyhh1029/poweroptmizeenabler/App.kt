package com.cyhh1029.poweroptmizeenabler

import android.app.Application
import org.lsposed.hiddenapibypass.HiddenApiBypass

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        HiddenApiBypass.addHiddenApiExemptions("")
        PowerOptimizationManager.init()
    }
}