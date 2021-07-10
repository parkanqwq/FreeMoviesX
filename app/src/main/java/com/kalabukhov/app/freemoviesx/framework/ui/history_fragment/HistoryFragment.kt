package com.kalabukhov.app.freemoviesx.framework.ui.history_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.databinding.FragmentHistoryBinding
import com.kalabukhov.app.freemoviesx.databinding.MainFragmentBinding
import com.kalabukhov.app.freemoviesx.framework.ui.adapter.HistoryAdapter
import com.kalabukhov.app.freemoviesx.framework.ui.settings_fragment.SettingsFragment
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.showSnackBar
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by inject()
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBarStyleLarge.visibility = View.GONE
                adapter.setData(appState.moviesData)
            }
            is AppState.Loading -> {
                historyFragmentRecyclerview.visibility = View.GONE
                progressBarStyleLarge.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBarStyleLarge.visibility = View.GONE
                historyFragmentRecyclerview.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reloading),
                    {
                        viewModel.getAllHistory()
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HistoryFragment()

//        const val BUNDLE_EXTRA_HISTORY = "history"
//
//        @JvmStatic
//        fun newInstance(bundle: Bundle): HistoryFragment {
//            val fragment = HistoryFragment()
//            fragment.arguments = bundle
//            return fragment
//        }
    }
}
