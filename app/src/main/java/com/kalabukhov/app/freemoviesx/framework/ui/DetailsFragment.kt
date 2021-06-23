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
        val movies = arguments?.getParcelable<Movies>(BUNDLE_EXTRA)
        movies?.let {
            it.nameMovie
            binding.nameMovie.text = it.nameMovie.nameMovie
            binding.starsMovie.text = CONST_STARS + it.nameMovie.starsMovie.toString()
            binding.country.text = CONST_COUNTRY + it.nameMovie.country
            binding.timeMovie.text = CONST_TIME_MOVIE + it.nameMovie.timeMovie
            binding.age.text = CONST_AGE + it.nameMovie.age.toString()
            binding.imageView.setImageResource(it.nameMovie.image)
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
