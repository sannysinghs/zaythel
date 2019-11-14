package com.muslimlab.prayers.ui.prayers

import com.muslimlab.prayers.model.PrayerResult
import com.muslimlab.prayers.model.Prayers
import io.reactivex.Single

interface PrayersRepository {
    fun fetchPrayers(city: String = "yangon", country: String = "mm"): Single<PrayerResult>
    fun todayPrayers(city: String = "yangon", country: String = "mm"): Single<Prayers>
}
