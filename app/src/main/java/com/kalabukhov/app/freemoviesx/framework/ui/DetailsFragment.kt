package com.kalabukhov.app.freemoviesx.framework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kalabukhov.app.freemoviesx.*
import com.kalabukhov.app.freemoviesx.databinding.FragmentDetailsBinding
import com.kalabukhov.app.freemoviesx.model.entites.Movies

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movies>(BUNDLE_EXTRA)?.let {
            with(binding){
                it.nameMovie
                nameMovie.text = it.nameMovie.nameMovie
                starsMovie.text = CONST_STARS + it.nameMovie.starsMovie.toString()
                country.text = CONST_COUNTRY + it.nameMovie.country
                timeMovie.text = CONST_TIME_MOVIE + it.nameMovie.timeMovie
                age.text = CONST_AGE + it.nameMovie.age.toString()
                imageView.setImageResource(it.nameMovie.image)
            }
        }
    }

    companion object {

        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
