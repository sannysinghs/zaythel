package com.muslimlab.prayers.ui.prayers

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlab.prayers.R
import com.muslimlab.prayers.databinding.FragmentPrayersBinding
import com.muslimlab.prayers.ui.utils.applyTransparentStatusBar

/**
 * A placeholder fragment containing a simple view.
 */
class PrayersFragment : Fragment() {

    private val viewModel = PrayersViewModel(LocalPrayersRepository())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentPrayersBinding.inflate(inflater).run {
        this.vm = viewModel
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onViewDestroyed()
    }
}
