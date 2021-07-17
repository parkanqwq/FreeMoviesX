package com.kalabukhov.app.freemoviesx.framework.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kalabukhov.app.freemoviesx.databinding.ItemNoteBinding
import com.kalabukhov.app.freemoviesx.model.database_note.NoteEntity
import com.kalabukhov.app.freemoviesx.model.entites.Movies
import com.kalabukhov.app.freemoviesx.model.entites.NoteMovie
import com.squareup.picasso.Picasso

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.RecyclerItemViewHolder>() {

    private var data: List<Movies> = arrayListOf()

    fun setData(data: List<Movies>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(private val binding: ItemNoteBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movies) = with(binding) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                nameMovie.text = data.nameMovie.original_title
                noteMovie.text = data.nameMovie.note
                Picasso
                    .get()
                    .load("https://image.tmdb.org/t/p/original"+data.nameMovie.backdrop_path)
                    .into(imageMovie)
            }
        }
    }
}