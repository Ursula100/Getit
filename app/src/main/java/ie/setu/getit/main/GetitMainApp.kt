package ie.setu.getit.main

import android.app.Application
import timber.log.Timber

class GetitMainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.i("Starting Getit Application")
    }
}