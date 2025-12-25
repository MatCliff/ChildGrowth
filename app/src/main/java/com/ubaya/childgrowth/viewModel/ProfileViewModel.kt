package com.ubaya.childgrowth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.childgrowth.model.Child
import com.ubaya.childgrowth.model.User
import com.ubaya.childgrowth.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val userLD = MutableLiveData<User>()
    val loadingLD = MutableLiveData<Boolean>()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            val db = buildDb(getApplication())
            userLD.postValue(db.userDao().getUser())
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
