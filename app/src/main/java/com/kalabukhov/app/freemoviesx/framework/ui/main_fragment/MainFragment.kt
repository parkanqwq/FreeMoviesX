package com.kalabukhov.app.freemoviesx.framework.ui.main_fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.framework.ui.adapter.AdapterMovies
import com.kalabukhov.app.freemoviesx.databinding.MainFragmentBinding
import com.kalabukhov.app.freemoviesx.framework.ui.contact_fragment.ContentProviderFragment
import com.kalabukhov.app.freemoviesx.framework.ui.contact_fragment.ContentProviderFragment.Companion.REQUEST_CODE
import com.kalabukhov.app.freemoviesx.framework.ui.details_fragment.DetailsFragment
import com.kalabukhov.app.freemoviesx.framework.ui.history_fragment.HistoryFragment
import com.kalabukhov.app.freemoviesx.framework.ui.maps_fragment.MapsFragment
import com.kalabukhov.app.freemoviesx.framework.ui.note_fragment.NoteFragment
import com.kalabukhov.app.freemoviesx.framework.ui.settings_fragment.SettingsFragment
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.entites.Movie
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.repository.ADULT
import com.kalabukhov.app.freemoviesx.model.repository.ID
import com.kalabukhov.app.freemoviesx.model.repository.QUERY
import com.kalabukhov.app.freemoviesx.showSnackBar
import com.kalabukhov.app.freemoviesx.showSnackBarLoading
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class MainFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movies: Movies) {
            activity?.supportFragmentManager?.let {
                val bundle = Bundle()
                bundle.putParcelable(MapsFragment.BUNDLE_EXTRA_MAP, movies)
                it.beginTransaction()
                    .add(R.id.container, MapsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }

    private val adapter = AdapterMovies(onObjectListener)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
        initView(view)
        ADULT = activity
            ?.getPreferences(Context.MODE_PRIVATE)
            ?.getBoolean(SettingsFragment.dataKeyAdult, false)
            ?: false
        recyclerView.adapter = adapter
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMoviesFilm()
        }
    }

    private fun renderData(appState: AppState) {
        with(binding){
            when (appState) {
                is AppState.Success -> {
                    loadingLayout.visibility = View.GONE
                    adapter.setMovies(appState.moviesData)
                }
                is AppState.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                    mainFragment.showSnackBarLoading(getString(R.string.loading))
                }
                is AppState.Error -> {
                    loadingLayout.visibility = View.GONE
                    mainFragment.showSnackBar(
                        getString(R.string.error),
                        getString(R.string.reloading),
                        { viewModel.getMoviesFilm() })
                }
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Movies)
    }

    companion object {
        fun newInstance() = MainFragment()
        private const val REFRESH_PERIOD = 5000L
        private const val MINIMAL_DISTANCE = 10f
    }

    private fun checkPermission() {
        activity?.let {
            when {
                ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED -> {
                    getLocation()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showRationaleDialog()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun showRationaleDialog() {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_rationale_title))
                .setMessage(getString(R.string.dialog_rationale_meaasge))
                .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                    requestPermission()
                }
                .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        checkPermissionsResult(requestCode, grantResults)
    }

    private fun checkPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                var grantedPermissions = 0
                if ((grantResults.isNotEmpty())) {
                    for (i in grantResults) {
                        if (i == PackageManager.PERMISSION_GRANTED) {
                            grantedPermissions++
                        }
                    }
                    if (grantResults.size == grantedPermissions) {
                        getLocation()
                    } else {
                        showDialog(
                            getString(R.string.dialog_title_no_gps),
                            getString(R.string.dialog_message_no_gps)
                        )
                    }
                } else {
                    showDialog(
                        getString(R.string.dialog_title_no_gps),
                        getString(R.string.dialog_message_no_gps)
                    )
                }
                return
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun getLocation() {
        activity?.let { context ->
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // Получить менеджер геолокаций
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)
                    provider?.let {
                        // Будем получать геоположение через каждые 60 секунд или каждые 100 метров
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            REFRESH_PERIOD,
                            MINIMAL_DISTANCE,
                            onLocationListener
                        )
                    }
                } else {
                    val location =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location == null) {
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_location_unknown)
                        )
                    } else {
                        getAddressAsync(context, location)
                        showDialog(
                            getString(R.string.dialog_title_gps_turned_off),
                            getString(R.string.dialog_message_last_known_location)
                        )
                    }
                }
            } else {
                showRationaleDialog()
            }
        }
    }

    private val onLocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddressAsync(it, location)
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    private fun getAddressAsync(
        context: Context,
        location: Location
    ) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )
                main_fragment.post {
                    showAddressDialog(addresses[0].getAddressLine(0), location)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun showAddressDialog(address: String, location: Location) {
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.dialog_address_title))
                .setMessage(address)
                .setPositiveButton(getString(R.string.dialog_address_get_weather)) { _, _ ->
                    openDetailsFragment(
                        Movies(
                            Movie(
                                580, address, location.latitude + location.longitude,
                                "", "", 1, "",
                                "", false, ""
//                                address,
//                                location.latitude,
//                                location.longitude
                            )
                        )
                    )
                }
                .setNegativeButton(getString(R.string.dialog_button_close)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private val adapter2 = AdapterMovies(object : OnItemViewClickListener {
        override fun onItemViewClick(movies: Movies) {
            openDetailsFragment(movies)
        }
    })

    private fun openDetailsFragment(
        movies: Movies
    ) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .add(
                    R.id.container,
                    DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, movies)
                    })
                )
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun initView(view: View) {
        val toolbar: Toolbar = initToolbar(view)
    }

    private fun initToolbar(view: View): Toolbar {
        val toolbar: Toolbar = view.findViewById(R.id.toolBar)
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return toolbar
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (navigateFragment(id)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun navigateFragment(id: Int): Boolean {
        when (id) {
            R.id.action_settings -> {
                activity?.supportFragmentManager?.let {
                    val bundle = Bundle()
                    bundle.putParcelable(SettingsFragment.BUNDLE_EXTRA_SETTINGS, null)
                    it.beginTransaction()
                        .add(R.id.container, SettingsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                return true
            }
            R.id.action_history -> {
                activity?.supportFragmentManager?.let {
//                    val bundle = Bundle()
//                    bundle.putParcelable(HistoryFragment.BUNDLE_EXTRA_HISTORY, null)
                    it.beginTransaction()
                        .add(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                return true
            }
            R.id.action_like -> {
                activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .add(R.id.container, NoteFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                return true
            }
            R.id.menu_content_provider -> {
                activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .add(R.id.container, ContentProviderFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                return true
            }
            R.id.action_local_map -> {
                checkPermission()
                val bundle = Bundle()
                bundle.putParcelable(MapsFragment.BUNDLE_EXTRA_MAP, null)
                activity?.supportFragmentManager?.let {
                    it.beginTransaction()
                        .add(R.id.container, MapsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                return true
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        val search = menu.findItem(R.id.action_search)
        val settings = menu.findItem(R.id.action_settings)
        val history = menu.findItem(R.id.action_history)
        val actionLike = menu.findItem(R.id.action_like)
        val actionLocalMap = menu.findItem(R.id.action_local_map)

        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               // QUERY = query
                ID = query.toInt()
                viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
                viewModel.getMoviesFilm()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //searchUsers(newText.toLowerCase()) в разработке
                return true
            }
        })
    }
}