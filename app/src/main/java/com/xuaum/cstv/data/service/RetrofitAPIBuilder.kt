package com.xuaum.cstv.data.service

import android.util.Log
import com.xuaum.cstv.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPIBuilder {
    companion object {
        private const val BASE_URL = "https://api.pandascore.co/csgo/"
        private const val API_KEY = "DOCezQO9pskdGcAVMzSKs5LUIGRJDoEIrqK5QPPNBILy2VTluv8"
    }

    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder()
                .also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                    val authInterceptor = Interceptor { chain ->
                        val newRequest = chain.request().newBuilder().addHeader(
                            "Authorization",
                            "Bearer $API_KEY"
                        ).build()
                        chain.proceed(newRequest)
                    }
                    client.addInterceptor(authInterceptor)

                }
                .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

}