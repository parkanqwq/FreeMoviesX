package com.kalabukhov.app.freemoviesx.framework.ui.note_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.databinding.FragmentNoteBinding
import com.kalabukhov.app.freemoviesx.framework.ui.adapter.NoteAdapter
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.showSnackBar
import org.koin.android.ext.android.inject

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by inject()
    private val adapter: NoteAdapter by lazy { NoteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        noteFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllNote()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                noteFragmentRecyclerview.visibility = View.VISIBLE
                progressBarStyleLarge.visibility = View.GONE
                adapter.setData(appState.moviesData)
            }
            is AppState.Loading -> {
                noteFragmentRecyclerview.visibility = View.GONE
                progressBarStyleLarge.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                noteFragmentRecyclerview.visibility = View.VISIBLE
                progressBarStyleLarge.visibility = View.GONE
                noteFragmentRecyclerview.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reloading),
                    {
                        viewModel.getAllNote()
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
            NoteFragment()

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
