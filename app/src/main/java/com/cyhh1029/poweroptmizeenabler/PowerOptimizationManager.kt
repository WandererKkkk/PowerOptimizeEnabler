package com.cyhh1029.poweroptmizeenabler

import android.app.usage.IUsageStatsManager
import android.os.IDeviceIdleController
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper

object PowerOptimizationManager {
    private const val PKG = "org.telegram.messenger"
    private const val BUCKET_WORKING_SET = 20
    private const val USER_SYSTEM = 0

    fun init() {
        if (Shizuku.checkSelfPermission() == 0) {
            run()
        } else {
            Shizuku.addBinderReceivedListener(object : Shizuku.OnBinderReceivedListener {
                override fun onBinderReceived() {
                    if (Shizuku.checkSelfPermission() == 0) {
                        run()
                        Shizuku.removeBinderReceivedListener(this)
                    }
                }
            })
        }
    }

    private fun run() = Thread {
        try {
            // Remove from DeviceIdle whitelist
            removeFromPowerSaveWhitelist()

            // Set app standby bucket to Working Set
            setAppStandbyBucket()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.start()

    private fun removeFromPowerSaveWhitelist() {
        val binder = SystemServiceHelper.getSystemService("deviceidle")
        val wrappedBinder = ShizukuBinderWrapper(binder)
        val deviceIdleController = IDeviceIdleController.Stub.asInterface(wrappedBinder)
        deviceIdleController.removePowerSaveWhitelistApp(PKG)
    }

    private fun setAppStandbyBucket() {
        val binder = SystemServiceHelper.getSystemService("usagestats")
        val wrappedBinder = ShizukuBinderWrapper(binder)
        val usageStatsManager = IUsageStatsManager.Stub.asInterface(wrappedBinder)
        usageStatsManager.setAppStandbyBucket(PKG, BUCKET_WORKING_SET, USER_SYSTEM)
    }
}