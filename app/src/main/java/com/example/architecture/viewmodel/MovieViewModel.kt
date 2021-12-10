package com.example.architecture.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.architecture.model.Movie
import com.example.architecture.repository.MovieRepository
import com.google.gson.Gson
import kotlinx.coroutines.*

class MovieViewModel constructor(private val repository: MovieRepository): ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Movie>>()
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable -> onError("Exception handled: ${throwable.localizedMessage}") }
    val loading = MutableLiveData<Boolean>()

    fun getAllMovie() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllMovie()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String){
        Log.d("Error", message)
//        errorMessage.value = message
//        loading.value = false
    }

    public override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}