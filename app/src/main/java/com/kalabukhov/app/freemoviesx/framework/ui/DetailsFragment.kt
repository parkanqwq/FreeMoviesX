package com.kalabukhov.app.freemoviesx.framework.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.kalabukhov.app.freemoviesx.*
import com.kalabukhov.app.freemoviesx.databinding.FragmentDetailsBinding
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.rest_entitites.MoviesDTO
import com.kalabukhov.app.freemoviesx.services.DetailsService
import com.kalabukhov.app.freemoviesx.services.LATITUDE_EXTRA
import org.koin.androidx.viewmodel.ext.android.viewModel

const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_DATA_EMPTY_EXTRA = "DATA IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_ID_EXTRA = "ID"
const val DETAILS_NAME_EXTRA = "NAME"
const val DETAILS_VOTE_AVERAGE_EXTRA = "VOTE_AVERAGE"
const val DETAILS_RELEASE_DATE_EXTRA = "RELEASE_DATE"
const val DETAILS_ORIGINAL_LANGUAGE_EXTRA = "ORIGINAL_LANGUAGE"
const val DETAILS_RUNTIME_EXTRA = "RUNTIME"
const val DETAILS_OVERVIEW_EXTRA = "OVERVIEW"

private const val PROCESS_ERROR = "Обработка ошибки"

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()
    private lateinit var movieBundle: Movies

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> error(DETAILS_INTENT_EMPTY_EXTRA)
                DETAILS_DATA_EMPTY_EXTRA -> error(DETAILS_DATA_EMPTY_EXTRA)
                DETAILS_RESPONSE_EMPTY_EXTRA -> error(DETAILS_RESPONSE_EMPTY_EXTRA)
                DETAILS_REQUEST_ERROR_EXTRA -> error(DETAILS_REQUEST_ERROR_EXTRA)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> error(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)
                DETAILS_URL_MALFORMED_EXTRA -> error(DETAILS_URL_MALFORMED_EXTRA)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    MoviesDTO(
                        intent.getIntExtra(DETAILS_ID_EXTRA, 580),
                        intent.getStringExtra(DETAILS_NAME_EXTRA),
                        intent.getDoubleExtra(DETAILS_VOTE_AVERAGE_EXTRA, 5.9),
                        intent.getStringExtra(DETAILS_RELEASE_DATE_EXTRA),
                        intent.getStringExtra(DETAILS_ORIGINAL_LANGUAGE_EXTRA),
                        intent.getIntExtra(DETAILS_RUNTIME_EXTRA, 9),
                        intent.getStringExtra(DETAILS_OVERVIEW_EXTRA)
                    )
                )
                else -> error(PROCESS_ERROR)
            }
        }
    }

    private fun error(error: String) {
        binding.detalsFragment.showSnackBar(
            error,
            getString(R.string.reloading),
            { getMovies() })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

    private var idForRequestInternet: Int? = 0
    private fun getMovies() {
        with(binding) {
        loadingLayout.visibility = View.VISIBLE
        context?.let {
            it.startService(Intent(it, DetailsService::class.java).apply {
                putExtra(
                    LATITUDE_EXTRA,
                    idForRequestInternet
                )
            })
        }
      }
    }

    private fun renderData(moviesDTO: MoviesDTO) {
        with(binding) {
            loadingLayout.visibility = View.GONE
            val name = moviesDTO.original_title
            if (name == null) {
                Toast.makeText(context, "Пустой запрос", Toast.LENGTH_SHORT).duration
                return
            } else {
                val movie = moviesDTO
                nameMovie.text = movie.original_title
                starsMovie.text = CONST_STARS + movie.vote_average.toString()
                age.text = CONST_AGE + movie.release_date
                var us: String? = movie.original_language
                    if (us.equals("en")){
                    us = "США"
                }
                country.text = CONST_COUNTRY +  us
                timeMovie.text = CONST_TIME_MOVIE +  movie.runtime.toString()
                tagline.text = movie.overview
                arguments?.getParcelable<Movies>(BUNDLE_EXTRA).let {
                    imageView.setImageResource(it!!.nameMovie.image)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Movies>(BUNDLE_EXTRA).let {
           idForRequestInternet = it?.nameMovie?.id!!
        }
        getMovies()
//        val movies = arguments?.getParcelable<Movies>(BUNDLE_EXTRA)
//            movies?.let {
//                with(binding) {
//                    nameMovie.text = it.nameMovie.name
//
//                    viewModel.liveDataToObserver.observe(viewLifecycleOwner, { appState ->
//                        when (appState) {
//                            is AppState.Error -> {
//                                loadingLayout.visibility = View.GONE
//                                loadingLayout.visibility = View.GONE
//                                detalsFragment.showSnackBar(
//                                    getString(R.string.error),
//                                    getString(R.string.reloading),
//                                    { viewModel.loadData(it.nameMovie.id) })
//                            }
//                            is AppState.Loading -> loadingLayout.visibility = View.VISIBLE
//                            is AppState.Success -> {
//                                loadingLayout.visibility = View.GONE
//                                nameMovie.text = appState.moviesData[0].original_title.toString()
//                                starsMovie.text = CONST_STARS + appState.moviesData[0].vote_average.toString()
//                                age.text = CONST_AGE + appState.moviesData[0].release_date.toString()
//                                var us: String? = appState.moviesData[0].original_language.toString()
//                                if (us.equals("en")){
//                                    us = "США"
//                                }
//                                country.text = CONST_COUNTRY + us
//                                timeMovie.text = CONST_TIME_MOVIE + appState.moviesData[0].runtime.toString()
//                                tagline.text = appState.moviesData[0].overview.toString()
//                                imageView.setImageResource(movies.nameMovie.image)
//                            }
//                        }
//                    })
//                    viewModel.loadData(it.nameMovie.id)
//                }
//        }
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
