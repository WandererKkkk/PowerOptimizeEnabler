package com.cyhh1029.poweroptmizeenabler

import android.app.usage.IUsageStatsManager
import android.content.pm.PackageManager
import android.os.IDeviceIdleController
import rikka.shizuku.Shizuku
import rikka.shizuku.ShizukuBinderWrapper
import rikka.shizuku.SystemServiceHelper

object PowerOptimizationManager {
    private var isInitialized = false

    private const val BUCKET_WORKING_SET = 20
    private const val USER_SYSTEM = 0

    private val PACKAGES = listOf(
        "org.telegram.messenger",
        "com.tencent.mobileqq",
        "nu.gpu.nagram",
        "com.google.android.gm",
        "com.kbzbank.kpaycustomer"
    )

    fun init() {
        if (isInitialized) return
        isInitialized = true

        Shizuku.addBinderReceivedListener { exec() }

        if (Shizuku.pingBinder()) {
            exec()
        }
    }

    private fun exec() {
        if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) return

        Thread {
            val idle = IDeviceIdleController.Stub.asInterface(
                ShizukuBinderWrapper(SystemServiceHelper.getSystemService("deviceidle"))
            )
            val stats = IUsageStatsManager.Stub.asInterface(
                ShizukuBinderWrapper(SystemServiceHelper.getSystemService("usagestats"))
            )

            PACKAGES.forEach { pkg ->
                idle.removePowerSaveWhitelistApp(pkg)
                stats.setAppStandbyBucket(pkg, BUCKET_WORKING_SET, USER_SYSTEM)
            }
        }.start()
    }
}
