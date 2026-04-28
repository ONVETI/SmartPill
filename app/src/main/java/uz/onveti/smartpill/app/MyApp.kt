package uz.onveti.smartpill.app

import android.app.Application
import uz.onveti.smartpill.di.initKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(applicationContext)
    }
}