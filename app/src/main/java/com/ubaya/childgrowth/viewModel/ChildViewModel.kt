package com.ubaya.childgrowth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.childgrowth.model.Child
import com.ubaya.childgrowth.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ChildViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val weightInput = MutableLiveData<String>()
    val heightInput = MutableLiveData<String>()
    val ageInput = MutableLiveData<String>()
    val growthLD = MutableLiveData<List<Child>>()
    val dataLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val job = Job()
    val messageLD = MutableLiveData<String>()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun addMeasurement() {
        val weight = weightInput.value?.toDoubleOrNull()
        val height = heightInput.value?.toDoubleOrNull()
        val age = ageInput.value?.toIntOrNull()

        if(weight == null || height == null || age == null) {
            messageLD.value = "Invalid input"
            return
        }

        val child = Child(weight, height, age)

        // Update LiveData
        val currentList = growthLD.value?.toMutableList() ?: mutableListOf()
        currentList.add(child)
        growthLD.value = currentList

        // Tambahkan ke DB
        addMeasure(child)

        // Reset input
        weightInput.value = ""
        heightInput.value = ""
        ageInput.value = ""

        messageLD.value = "Data added"
    }

    fun addMeasure(child: Child) {
        launch {
            val db = buildDb(getApplication())
            db.childDao().insertAll(child)
        }
    }

    fun refresh() {
        loadingLD.postValue(true)
        dataLoadErrorLD.postValue(false)

        launch {
            val db = buildDb(getApplication())
            growthLD.postValue(db.childDao().selectAllChild())
            loadingLD.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}