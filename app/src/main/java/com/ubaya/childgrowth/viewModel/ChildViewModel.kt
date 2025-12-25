package com.ubaya.childgrowth.viewModel

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

    val growthLD = MutableLiveData<List<Child>>()
    val dataLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

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