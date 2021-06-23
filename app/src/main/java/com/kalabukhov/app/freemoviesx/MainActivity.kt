package com.kalabukhov.app.freemoviesx

import android.os.Bundle
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

const val CONST_COUNTRY = "Страна: "
const val CONST_STARS = "Рейтинг: "
const val CONST_AGE = "Год: "
const val CONST_TIME_MOVIE = "Продолжительность: "

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commit()
        }
    }
}