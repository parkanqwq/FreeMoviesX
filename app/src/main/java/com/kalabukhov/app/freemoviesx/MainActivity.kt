package com.kalabukhov.app.freemoviesx

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.kalabukhov.app.freemoviesx.framework.ui.MainFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawerlayout)
       // initView() работает метод, есть выше писать не drawerlayout а лояут , но тогда
        // не работает ФрагментАктивити и работа с ресайклВью.
        // не пойму что нужно выше или ниже правильно указать... поэтому делаю во фрагменте меню
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.drawer_layout, MainFragment.newInstance())
                .commit()
        }
    }
//
//    private fun initView() {
//        val toolbar: Toolbar = initToolbar()
//        initDrawer(toolbar)
//    }
//
//    private fun initToolbar(): Toolbar {
//        val toolbar = findViewById<Toolbar>(R.id.toolBar)
//        setSupportActionBar(toolbar)
//        return toolbar
//    }
//
//    private fun initDrawer(toolbar: Toolbar) {
//        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//        val toggle = ActionBarDrawerToggle(
//            this, drawer, toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawer.addDrawerListener(toggle)
//        toggle.syncState()
//
//        val navigationView: NavigationView = findViewById(R.id.nav_view)
//
//        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
//            val id = item.itemId
//            if (navigateFragment(id)) {
//                drawer.closeDrawer(GravityCompat.START)
//                return@setNavigationItemSelectedListener true
//            }
//            false
//        }
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        return if (navigateFragment(id)) {
//            true
//        } else super.onOptionsItemSelected(item)
//    }
//
//    private fun navigateFragment(id: Int): Boolean {
//        when (id) {
//            R.id.action_addObj -> {
//
//                return true
//            }
//            R.id.action_lookObj -> {
//
//                return true
//            }
//        }
//        return false
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        val search = menu.findItem(R.id.action_search)
//        val searchText = search.actionView as SearchView
//        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                //searchUsers(newText.toLowerCase())
//                return true
//            }
//        })
//        return true
//    }
}