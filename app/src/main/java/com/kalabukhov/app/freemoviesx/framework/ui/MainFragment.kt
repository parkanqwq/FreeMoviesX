package com.kalabukhov.app.freemoviesx.framework.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.adapter.AdapterMovies
import com.kalabukhov.app.freemoviesx.databinding.MainFragmentBinding
import com.kalabukhov.app.freemoviesx.model.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        initView(view)
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getMoviesFilm()
    }

    private fun renderData(appState: AppState) = with(binding){
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AdapterMovies(context, appState)
    }

    private fun initView(view: View) {
        val toolbar: Toolbar = initToolbar(view)
       // initDrawer(view, toolbar)  почему то не работает, 7 часов потратил и не понял почему
            // в активити если писать тоже саоме, работает, а тут нет(((
    }

    private fun initToolbar(view: View): Toolbar {
        val toolbar: Toolbar = view.findViewById(R.id.toolBar)
        val appCompatActivity = activity as AppCompatActivity?
        appCompatActivity!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return toolbar
    }

     private fun initDrawer(view: View, toolbar: Toolbar) {
         val drawerLayout: DrawerLayout = view.findViewById(R.id.drawer_layout)
         val navView: NavigationView? = view.findViewById(R.id.nav_view)
             // думаю проблема в  AppCompatActivity(), нужно писать что то другое... помогите
         val toggle = ActionBarDrawerToggle(
             AppCompatActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
         )
         drawerLayout.addDrawerListener(toggle)
         toggle.syncState()

         navView?.setNavigationItemSelectedListener { item: MenuItem ->
            val id = item.itemId
            if (navigateFragment(id)) {
                drawerLayout.closeDrawer(GravityCompat.START)
                return@setNavigationItemSelectedListener true
            }
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (navigateFragment(id)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun navigateFragment(id: Int): Boolean {
        when (id) {
            R.id.action_addObj -> {
                // меню для шторки но она не работает
                return true
            }
            R.id.action_lookObj -> {
                // меню для шторки но она не работает
                return true
            }
            R.id.action_settings -> {
                Toast.makeText(context, "settings", Toast.LENGTH_SHORT).show()
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

    companion object {
        fun newInstance() = MainFragment()
    }
}

