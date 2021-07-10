package com.kalabukhov.app.freemoviesx.framework.ui.settings_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kalabukhov.app.freemoviesx.databinding.SettingsFragmentBinding
import com.kalabukhov.app.freemoviesx.model.repository.ADULT

class SettingsFragment() : Fragment() {

    private lateinit var binding : SettingsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            ADULT = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getBoolean(dataKeyAdult, false)
                ?: false

            switchAdult.isChecked = ADULT

            switchAdult.setOnCheckedChangeListener{ _ , isChecked ->

                if (isChecked) {
                    ADULT = true
                    val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
                    editor?.putBoolean(dataKeyAdult, ADULT)
                    editor?.apply()
                    activity?.recreate()
                } else {
                    ADULT = false
                    val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
                    editor?.putBoolean(dataKeyAdult, ADULT)
                    editor?.apply()
                    activity?.recreate()
                }
            }
          //  val a = activity?.getSharedPreferences("key", Context.MODE_PRIVATE)
            // созранить на весь проект
        }
    }

    companion object {
        const val BUNDLE_EXTRA_SETTINGS = "settings"
        const val dataKeyAdult = "dataKeyAdult"

        fun newInstance(bundle: Bundle): SettingsFragment {
            val fragment = SettingsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}