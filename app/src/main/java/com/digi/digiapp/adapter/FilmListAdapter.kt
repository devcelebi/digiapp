package com.digi.digiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.digi.digiapp.ExxoPlayerFragment
import com.digi.digiapp.R
import com.digi.digiapp.network.FilmInfo

class FilmListAdapter(
    private var films: List<FilmInfo>
) : RecyclerView.Adapter<FilmListAdapter.FilmInfoViewHolder>(){

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
            fun onItemClick(position: Int)
    }

    fun setonItemClickListener(listener: onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmInfoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.film_item, parent, false)
        return FilmInfoViewHolder(view,mListener)
    }

    override fun getItemCount(): Int = films.size

    override fun onBindViewHolder(holder: FilmInfoViewHolder, position: Int) {
        holder.bind(films[position])
    }


    inner class FilmInfoViewHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val name: TextView = itemView.findViewById(R.id.name)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
        fun bind(filmInfo: FilmInfo) {
            name.text = filmInfo.title


            Glide.with(itemView)
                .load("http://image.tmdb.org/t/p/w185${filmInfo.poster_path}")
                .transform(CenterCrop())
                .into(poster)
        }
    }
}