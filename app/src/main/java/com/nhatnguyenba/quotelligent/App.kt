package com.nhatnguyenba.quotelligent

import android.app.Application
import com.google.firebase.FirebaseApp
import com.nhatnguyenba.quotelligent.config.RemoteConfigManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var remoteConfigManager: RemoteConfigManager

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        remoteConfigManager.fetchConfig()
    }
}