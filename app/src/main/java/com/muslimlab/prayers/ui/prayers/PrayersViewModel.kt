package com.muslimlab.prayers.ui.prayers

import android.text.format.DateFormat
import androidx.databinding.ObservableField
import com.muslimlab.prayers.model.Prayers
import com.muslimlab.prayers.ui.utils.BURMESE_NUMBERS
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class PrayersViewModel(private val repository: PrayersRepository) {

    private lateinit var prayerDisposable: Disposable
    val prayers = ObservableField<Prayers>()
    val today = ObservableField<String>()

    fun fetchPrayers() = repository.fetchPrayers()
        .map { it.data }



    fun onViewCreated() {
        prayerDisposable = fetchPrayers()
            .doOnSuccess {
                it.firstOrNull()?.run {
                    timings.run {
                        val data = Prayers (
                            imsak =  imsak.to12HourFormat().toBurmeseNumber(),
                            fajir = fajir.to12HourFormat().toBurmeseNumber(),
                            dhuhr = dhuhr.to12HourFormat().toBurmeseNumber(),
                            asr = asr.to12HourFormat().toBurmeseNumber(),
                            maghrib = maghrib.to12HourFormat().toBurmeseNumber(),
                            isha = isha.to12HourFormat().toBurmeseNumber(),
                            sunrise = sunrise.to12HourFormat().toBurmeseNumber(),
                            sunset = ""
                        )
                        prayers.set(data)
                    }
                }
            }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun onViewDestroyed() {
        if (!prayerDisposable.isDisposed) {
            prayerDisposable.dispose()
        }
    }


}

private fun String.toBurmeseNumber(): String {
    return " ${BURMESE_NUMBERS[split(":").first().toInt()]} : ${ BURMESE_NUMBERS[split(":").last().toInt()]}"
}

val FORMAT_PRAYER_TIME_12 = SimpleDateFormat("hh:mm", Locale.ENGLISH)
private fun String.to12HourFormat(): String {
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR, split(":").first().toInt())
        set(Calendar.MINUTE, split(":").last().toInt())
    }

    return FORMAT_PRAYER_TIME_12.format(cal.time)
}
