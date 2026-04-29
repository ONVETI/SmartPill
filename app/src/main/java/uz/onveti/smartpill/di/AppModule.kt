package uz.onveti.smartpill.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import uz.onveti.smartpill.notification.MedicineNotificationScheduler

val appModule = module {
    single { MedicineNotificationScheduler(androidContext()) }
}
