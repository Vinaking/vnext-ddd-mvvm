package com.example.architecture.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.architecture.R
import com.example.architecture.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val context: Context): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val movies = ArrayList<Movie>()

    fun setMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
//        val binding: ViewDataBinding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),R.layout.item_movie, parent, false)
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        val movie = movies[position]
//        holder.bind(movie)
        holder.itemView.tvName.text = movie.name
    }

    override fun getItemCount(): Int {
        return movies.size
    }

//    inner class ViewHolder(private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(movie: Movie) {
//            binding.setVariable(1, movie)
//        }
//    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}