package com.kalabukhov.app.freemoviesx.framework.ui.main_fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.framework.ui.adapter.AdapterMovies
import com.kalabukhov.app.freemoviesx.databinding.MainFragmentBinding
import com.kalabukhov.app.freemoviesx.framework.ui.DetailsFragment
import com.kalabukhov.app.freemoviesx.model.AppState
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.showSnackBar
import com.kalabukhov.app.freemoviesx.showSnackBarLoading
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

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
                    adapter.setWeather(appState.moviesData)
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
                main_fragment.showSnackBarLoading(getString(R.string.settings))
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

        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //searchUsers(newText.toLowerCase()) в разработке
                return true
            }
        })
    }
}