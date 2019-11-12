package com.muslimlab.prayers.ui.prayers

import android.content.Context
import androidx.databinding.ObservableField
import com.muslimlab.prayers.model.Prayers
import com.muslimlab.prayers.ui.utils.*
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.io.PipedReader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PrayersViewModel(
    private val repository: PrayersRepository
) {

    val prayerDisposable: MutableList<Disposable> = ArrayList(5)
    val prayers = ObservableField<Prayers>()
    val today = ObservableField<String>()
    val dayOfTheWeek = ObservableField<String>()

    fun fetchPrayers() = repository.fetchPrayers()
        .map { it.data }



    fun onViewCreated() {
        prayerDisposable.add(Single.fromCallable { Calendar.getInstance().time }
            .map {
                Pair(
                    FORMAT_PRAYER_TODAY.format(it).run {
                        "${split(":")[0].toBurmeseDay()}.${split(":")[1].toBurmeseMonth()}"
                    },
                    FORMAT_PRAYER_WEEK_OF_DAY.format(it).toBurmeseDayOfWeek()
                )
            }
            .doOnSuccess {
                today.set(it.first)
                dayOfTheWeek.set(it.second)
            }.subscribe())

        prayerDisposable.add(fetchPrayers()
            .doOnSuccess {
                it.firstOrNull()?.run {
                    timings.run {
                        val data = Prayers(
                            imsak = imsak.to12HourFormat().toBurmeseNumber(),
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
            .subscribe()
        )
    }

    fun onViewDestroyed() {
        prayerDisposable.forEach {
            if (!it.isDisposed) it.dispose()
        }
    }


}

private fun String.toBurmeseDayOfWeek(): String {
    return BURMESE_DAY_OF_WEEK[toLowerCase(Locale.ENGLISH)]?.plus(BUREMSE_DAY_STRING) ?: ""
}

private fun String.toBurmeseDay(): String = BURMESE_NUMBERS[this.toInt()].plus(BUREMSE_YAT_STRING).plus(
    BUREMSE_DAY_STRING)

private fun String.toBurmeseMonth(): String = BURMESE_MONTHS[this]?.plus(BUREMSE_MONTH_STRING) ?: ""


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
