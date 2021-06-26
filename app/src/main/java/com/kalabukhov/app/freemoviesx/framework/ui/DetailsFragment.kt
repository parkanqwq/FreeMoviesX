package com.kalabukhov.app.freemoviesx.framework.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.kalabukhov.app.freemoviesx.*
import com.kalabukhov.app.freemoviesx.databinding.FragmentDetailsBinding
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import kotlinx.android.synthetic.main.item_movies.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()
    private lateinit var movieBundle: Movies

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
                with(binding) {
                    nameMovie.text = it.nameMovie.name

                    viewModel.liveDataToObserver.observe(viewLifecycleOwner, { appState ->
                        when (appState) {
                            is AppState.Error -> {
                                loadingLayout.visibility = View.GONE
                                loadingLayout.visibility = View.GONE
                                detalsFragment.showSnackBar(
                                    getString(R.string.error),
                                    getString(R.string.reloading),
                                    { viewModel.loadData(it.nameMovie.id) })
                            }
                            is AppState.Loading -> loadingLayout.visibility = View.VISIBLE
                            is AppState.Success -> {
                                loadingLayout.visibility = View.GONE
                                nameMovie.text = appState.moviesData[0].original_title.toString()
                                starsMovie.text = CONST_STARS + appState.moviesData[0].vote_average.toString()
                                age.text = CONST_AGE + appState.moviesData[0].release_date.toString()
                                var us: String? = appState.moviesData[0].original_language.toString()
                                if (us.equals("en")){
                                    us = "США"
                                }
                                country.text = CONST_COUNTRY + us
                                timeMovie.text = CONST_TIME_MOVIE + appState.moviesData[0].runtime.toString()
                                tagline.text = appState.moviesData[0].overview.toString()
                                imageView.setImageResource(movies.nameMovie.image)
                            }
                        }
                    })
                    viewModel.loadData(it.nameMovie.id)
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
