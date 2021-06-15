package com.kalabukhov.app.freemoviesx.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.adapter.AdapterMovies.UserViewHolder
import com.kalabukhov.app.freemoviesx.model.AppState

class AdapterMovies(private val mContext: Context?,private val appState: AppState) :
    RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        when (appState) {
            is AppState.Success -> {
                val moviesData = appState.moviesData
                holder.loadingLayout.visibility = View.GONE
                holder.nameMovie.text = moviesData.nameMovie.nameMovie
                holder.starsMovie.text = moviesData.starsMovie.starsMovie.toString()
            }
            is AppState.Loading -> {
                holder.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                holder.loadingLayout.visibility = View.GONE
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameMovie: TextView
        var starsMovie: TextView
        var loadingLayout: FrameLayout

        init {
            nameMovie = itemView.findViewById(R.id.nameMovie)
            starsMovie = itemView.findViewById(R.id.starsMovie)
            loadingLayout = itemView.findViewById(R.id.loadingLayout)
        }
    }
}