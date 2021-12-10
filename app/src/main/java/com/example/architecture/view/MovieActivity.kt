package com.example.architecture.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.architecture.R
import com.example.architecture.api.MovieService
import com.example.architecture.databinding.ActivityMovieBinding
import com.example.architecture.model.Movie
import com.example.architecture.repository.MovieRepository
import com.example.architecture.viewmodel.MovieViewModel
import com.example.architecture.viewmodel.ViewModelFactory
import com.example.navigation_ui.NavgationActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.inappmessaging.FirebaseInAppMessaging
import com.google.firebase.inappmessaging.model.MessageType
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter
    private lateinit var binding: ActivityMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        val retrofit = MovieService.getInstance()
        val repository = MovieRepository(retrofit)

        val movie = Movie(name = "takeouta", "", "")
        binding.movie = movie

        adapter = MovieAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MovieViewModel::class.java]

        viewModel.movieList.observe(this, { adapter.setMovies(it); adapter.notifyDataSetChanged() })
        viewModel.loading.observe(this, {

        })
        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.getAllMovie()


//        startActivity(Intent(this, NavgationActivity::class.java))

        Log.d("FIAM", "log")
        FirebaseInAppMessaging.getInstance().isAutomaticDataCollectionEnabled = true

        startActivity(Intent(this, MainActivity::class.java))

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        viewModel.onCleared()
        super.onDestroy()
    }
}