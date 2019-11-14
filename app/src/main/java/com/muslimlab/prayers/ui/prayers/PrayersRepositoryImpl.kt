package com.muslimlab.prayers.ui.prayers

import com.muslimlab.prayers.model.PrayerResult
import com.muslimlab.prayers.model.Prayers
import io.reactivex.Single
import retrofit2.Retrofit

class PrayersRepositoryImpl(private val prayersApi: PrayersApi): PrayersRepository {
    override fun fetchPrayers(city: String, country: String): Single<PrayerResult> {
        return prayersApi.todayPrayers(city, country)
    }

    override fun todayPrayers(city: String, country: String): Single<Prayers> {
        return Single.just(null)
    }
}