package com.kalabukhov.app.freemoviesx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kalabukhov.app.freemoviesx.framework.ui.main_fragment.MainFragment

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