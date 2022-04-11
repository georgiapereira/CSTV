package com.xuaum.cstv.data.service

import com.xuaum.cstv.BuildConfig
import com.xuaum.cstv.util.Constants.API_KEY
import com.xuaum.cstv.util.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAPIBuilder : ApiBuilder {

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
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

    override fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }

}