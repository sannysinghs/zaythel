package com.zaythel.consumer

import android.app.Application
import retrofit2.Retrofit

class PrayersApplication: Application() {


    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        /*retrofit = Retrofit.Builder()
            .baseUrl("https://aladhan.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()*/
    }

    fun getRetrofitInstance() = retrofit
}