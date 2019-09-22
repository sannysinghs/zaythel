package com.muslimlab.prayers.ui.prayers

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlab.prayers.R

/**
 * A placeholder fragment containing a simple view.
 */
class PrayersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prayers, container, false)
    }
}
