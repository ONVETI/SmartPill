package uz.onveti.smartpill.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

data class BaseUrl(val url: String)
data class ApiKey(val key: String)

val networkModule = { baseUrl: BaseUrl ->

    module {
        single {
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                isLenient = true
            }
        }

        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        single {
            val contentType = "application/json".toMediaType()
            Retrofit.Builder()
                .baseUrl(baseUrl.url)
                .client(get())
                .addConverterFactory(get<Json>().asConverterFactory(contentType))
                .build()
        }
    }
}
