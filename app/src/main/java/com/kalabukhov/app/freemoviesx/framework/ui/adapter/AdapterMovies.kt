package com.kalabukhov.app.freemoviesx.framework.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.freemoviesx.CONST_COUNTRY
import com.kalabukhov.app.freemoviesx.CONST_STARS
import com.kalabukhov.app.freemoviesx.databinding.ItemMoviesBinding
import com.kalabukhov.app.freemoviesx.framework.ui.main_fragment.MainFragment
import com.kalabukhov.app.freemoviesx.model.entites.Movies

class AdapterMovies(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
RecyclerView.Adapter<AdapterMovies.MainViewHolder>() {

    private var movieData: List<Movies> = listOf()
    private lateinit var binding: ItemMoviesBinding

    fun setWeather(data: List<Movies>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
       binding = ItemMoviesBinding.inflate(
           LayoutInflater.from(parent.context), parent, false
       )
        return MainViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount() = movieData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movies) = with(binding){
            nameMovie.text = movie.nameMovie.name
            starsMovie.text = CONST_STARS +  movie.nameMovie.starsMovie.toString()
            country.text = CONST_COUNTRY + movie.nameMovie.original_language
            timeMovie.text = movie.nameMovie.runtime.toString()
            imageMovie.setImageResource(movie.nameMovie.image)
            root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(movie)
            }
        }
    }
}