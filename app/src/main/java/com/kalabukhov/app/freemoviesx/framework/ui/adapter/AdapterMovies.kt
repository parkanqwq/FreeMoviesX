package com.kalabukhov.app.freemoviesx.framework.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArraySet
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.freemoviesx.CONST_COUNTRY
import com.kalabukhov.app.freemoviesx.CONST_STARS
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.databinding.ItemMoviesBinding
import com.kalabukhov.app.freemoviesx.framework.ui.main_fragment.MainFragment
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.squareup.picasso.Picasso

class AdapterMovies(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
RecyclerView.Adapter<AdapterMovies.MainViewHolder>() {

    private var movieData: List<Movies> = listOf()

    //private lateinit var binding: ItemMoviesBinding

//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return position
//    }

    fun setMovies(data: List<Movies>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MainViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.item_movies, parent, false) as View)

//    : MainViewHolder {
//       binding = ItemMoviesBinding.inflate(
//           LayoutInflater.from(parent.context), parent, false
//       )
//        return MainViewHolder(binding.root)
//    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        //holder.setIsRecyclable(false)
        holder.bind(movieData[position])
    }

    override fun getItemCount() = movieData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMoviesBinding.bind(view)
        fun bind(movie: Movies) = with(binding){
            nameMovie.text = movie.name
            starsMovie.text = movie.place_of_birth
//            starsMovie.text = CONST_STARS +  movie.nameMovie.vote_average.toString()
//            country.text = CONST_COUNTRY + movie.nameMovie.original_language
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/original"+movie.poster_path)
                .into(imageMovie)
            root.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(movie)
            }
        }
    }
}