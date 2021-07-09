package com.kalabukhov.app.freemoviesx.framework.ui.main_fragment

import android.content.Context
import android.content.SharedPreferences
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.framework.ui.adapter.AdapterMovies
import com.kalabukhov.app.freemoviesx.databinding.MainFragmentBinding
import com.kalabukhov.app.freemoviesx.framework.ui.details_fragment.DetailsFragment
import com.kalabukhov.app.freemoviesx.framework.ui.details_fragment.DetailsFragment.Companion.BUNDLE_EXTRA
import com.kalabukhov.app.freemoviesx.framework.ui.history_fragment.HistoryFragment
import com.kalabukhov.app.freemoviesx.framework.ui.note_fragment.NoteFragment
import com.kalabukhov.app.freemoviesx.framework.ui.settings_fragment.SettingsFragment
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.repository.ADULT
import com.kalabukhov.app.freemoviesx.model.repository.QUERY
import com.kalabukhov.app.freemoviesx.showSnackBar
import com.kalabukhov.app.freemoviesx.showSnackBarLoading
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    private val onObjectListener = object : OnItemViewClickListener {
        override fun onItemViewClick(movies: Movies) {
            activity?.supportFragmentManager?.let {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, movies)
                it.beginTransaction()
                    .add(R.id.container, DetailsFragment.newInstance(bundle))
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

        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                QUERY = query
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