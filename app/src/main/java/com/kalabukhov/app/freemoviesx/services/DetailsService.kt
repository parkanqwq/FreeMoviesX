package com.kalabukhov.app.freemoviesx.services

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.kalabukhov.app.freemoviesx.framework.ui.*
import com.kalabukhov.app.freemoviesx.model.rest.rest_entitites.MoviesDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val LATITUDE_EXTRA = "Latitude"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class DetailsService(name: String = "DetailService") : IntentService(name) {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val id = intent.getIntExtra(LATITUDE_EXTRA, 0)
            if (id == 0) {
                onEmptyData()
            } else {
                loadMovies(id)
            }
        }
    }

    private fun loadMovies(id: Int) {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${id}?api_key=a7fe7b51456e94640008bbb9e3a50dd5")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                }
                urlConnection.readTimeout = REQUEST_TIMEOUT

                val moviesDTO: MoviesDTO =
                    Gson().fromJson(
                        getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                        MoviesDTO::class.java)
                onResponse(moviesDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(moviesDTO: MoviesDTO) {
        val fact = moviesDTO
        if (fact == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(
                fact.id,
                fact.original_title,
                fact.vote_average,
                fact.release_date,
                fact.original_language,
                fact.runtime,
                fact.overview,
                fact.backdrop_path
            )
        }
    }

    private fun onSuccessResponse(id: Int?, name: String?, vote_average: Double?,
                                  release_date: String?, original_language: String?,
                                  runtime: Int?, overview: String?, backdrop_path: String?) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_ID_EXTRA, id)
        broadcastIntent.putExtra(DETAILS_NAME_EXTRA, name)
        broadcastIntent.putExtra(DETAILS_NAME_EXTRA, name)
        broadcastIntent.putExtra(DETAILS_VOTE_AVERAGE_EXTRA, vote_average)
        broadcastIntent.putExtra(DETAILS_RELEASE_DATE_EXTRA, release_date)
        broadcastIntent.putExtra(DETAILS_ORIGINAL_LANGUAGE_EXTRA, original_language)
        broadcastIntent.putExtra(DETAILS_RUNTIME_EXTRA, runtime)
        broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, overview)
        broadcastIntent.putExtra(DETAILS_backdrop_path_EXTRA, backdrop_path)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)

    }
}