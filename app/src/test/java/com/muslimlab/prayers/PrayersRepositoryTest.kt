package com.muslimlab.prayers

import com.muslimlab.prayers.ui.prayers.LocalPrayersRepository
import com.muslimlab.prayers.ui.prayers.PrayersRepository
import org.junit.Test

class PrayersRepositoryTest {

    private val repo = LocalPrayersRepository()

    @Test
    fun testFetchPrayers() {
        repo.fetchPrayers()
            .test()
            .assertNoErrors()
            .assertValue { it.data.isNotEmpty() && it.data.first().timings.asr.equals("15:24 (+0630)") }
            .dispose()
    }
}