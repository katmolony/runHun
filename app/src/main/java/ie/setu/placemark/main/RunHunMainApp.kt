package ie.setu.placemark.main

import android.app.Application
import timber.log.Timber


class RunHunMainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Starting Run Hun Application")
    }
}