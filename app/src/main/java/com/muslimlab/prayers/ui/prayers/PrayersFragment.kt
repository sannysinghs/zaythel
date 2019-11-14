package com.muslimlab.prayers.ui.prayers

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muslimlab.prayers.PrayersApplication
import com.muslimlab.prayers.R
import com.muslimlab.prayers.databinding.FragmentPrayersBinding
import com.muslimlab.prayers.ui.utils.applyTransparentStatusBar

/**
 * A placeholder fragment containing a simple view.
 */
class PrayersFragment : Fragment() {

    lateinit var viewModel: PrayersViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = PrayersViewModel(
            PrayersRepositoryImpl(
                (activity?.application as PrayersApplication).getRetrofitInstance().create(
                    PrayersApi::class.java
                )
            )
        )
    }


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
