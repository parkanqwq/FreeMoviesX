package com.kalabukhov.app.freemoviesx.framework.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.freemoviesx.R
import com.kalabukhov.app.freemoviesx.databinding.ItemHistoryRecyclerviewBinding
import com.kalabukhov.app.freemoviesx.databinding.ItemMoviesBinding
import com.kalabukhov.app.freemoviesx.model.database.Database
import com.kalabukhov.app.freemoviesx.model.database.HistoryEntity
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.repository.Repository
import kotlinx.android.synthetic.main.item_history_recyclerview.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Movies> = arrayListOf()

    fun setData(data: List<Movies>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            ItemHistoryRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val binding: ItemHistoryRecyclerviewBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movies) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                recyclerViewItem.text =
                    String.format("%s %s %s", data.nameMovie.original_title,
                        data.nameMovie.release_date, data.nameMovie.overview)
                root.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.nameMovie.original_title}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

