package com.example.architecture.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architecture.model.NoteItem
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel(), Observable {
    val isStringEmpty = MutableLiveData<Boolean>()
    @Bindable
    val inputTitle = MutableLiveData<String>()
    @Bindable
    val inputDescription = MutableLiveData<String>()
    val list = MutableLiveData<ArrayList<NoteItem>>()
    private val arrlist = ArrayList<NoteItem>()

    init {
        isStringEmpty.value = false
    }

    fun addData() {
        viewModelScope.launch {
            val title = inputTitle.value ?: ""
            val description = inputDescription.value ?: ""
            if (title == "" || description == "") {
                isStringEmpty.value = true
            } else {
                val noteItem = NoteItem(title = title, description = description)
                arrlist.add(noteItem)
                list.value = arrlist
            }
        }
    }

    fun clearData() {
        arrlist.clear()
        list.value = arrlist
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}