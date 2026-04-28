package uz.onveti.smartpill.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(
    context: Context,
) {
    startKoin {
        androidContext(context)
        modules(
            appModule,
            networkModule(BaseUrl("https://api.twelvedata.com/")),
            module { single { ApiKey("0ba99a0570884119b3077dbfe1952840") } }, // Twelve Data API Key
            dataModule,
            screensModule,
        )
    }
}