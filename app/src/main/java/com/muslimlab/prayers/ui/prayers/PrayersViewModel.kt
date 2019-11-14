package com.muslimlab.prayers.ui.prayers

import android.content.Context
import androidx.databinding.ObservableField
import com.muslimlab.prayers.model.Prayers
import com.muslimlab.prayers.ui.utils.*
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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

        prayerDisposable.add(repository.fetchPrayers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                it.data.timings.run {
                    val data = Prayers(
                        imsak = "${BURMESE_DAY_TIME.MORNING.value} ${imsak.to12HourFormat().toBurmeseNumber()}",
                        fajir = "${BURMESE_DAY_TIME.MORNING.value} ${fajir.to12HourFormat().toBurmeseNumber()}" ,
                        dhuhr = "${BURMESE_DAY_TIME.NOON.value} ${dhuhr.to12HourFormat().toBurmeseNumber()}",
                        asr = "${BURMESE_DAY_TIME.EVENING.value} ${asr.to12HourFormat().toBurmeseNumber()}",
                        maghrib = "${BURMESE_DAY_TIME.EVENING.value} ${maghrib.to12HourFormat().toBurmeseNumber()}",
                        isha = "${BURMESE_DAY_TIME.NIGHT.value} ${isha.to12HourFormat().toBurmeseNumber()}",
                        sunrise = sunrise.to12HourFormat().toBurmeseNumber(),
                        sunset = ""
                    )
                    prayers.set(data)
                }

            }
            .doOnError { println(it) }
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

val FORMAT_PRAYER_TIME_12 = SimpleDateFormat("HH:mm", Locale.ENGLISH)
private fun String.to12HourFormat(): String {
    val cal = Calendar.getInstance().apply {
        set(Calendar.HOUR, split(":").first().toInt())
        set(Calendar.MINUTE, split(":").last().toInt())
    }

    return FORMAT_PRAYER_TIME_12.format(cal.time)
}
