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
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(application: Application) :
    AndroidViewModel(application), CoroutineScope {

    val userLD = MutableLiveData<User>()
    val nameInput = MutableLiveData<String>()
    val bodInput = MutableLiveData<String>()
    val genderInput = MutableLiveData<Int>() // 0 = Male, 1 = Female
    val showDatePickerEvent = MutableLiveData<Boolean>()
    val showToastLD = MutableLiveData<String>()  // LiveData untuk notifikasi


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        launch {
            val db = buildDb(getApplication())
            var user = db.userDao().getUser()

            //DEFAULT PROFILE
            if (user == null) {
                user = User(
                    name = "Udin Dindin",
                    bod = System.currentTimeMillis(),
                    gender = 0 //0 = Male, 1 = Female
                )
                db.userDao().insertUser(user)
            }

            userLD.postValue(user)
            bodInput.postValue(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(user.bod))
        }
    }
    fun onDatePickerClicked() {
        showDatePickerEvent.value = true
    }

    fun saveProfile() {
        val name = userLD.value?.name ?: return
        val gender = userLD.value?.gender ?: 0
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val bodMillis = sdf.parse(bodInput.value ?: "")?.time ?: return
        val currentUser = userLD.value ?: return
        if(currentUser.name.isBlank()) {
            showToastLD.value = "Name cannot be empty"
            return
        }
        showToastLD.postValue("Profile saved successfully")
        launch {
            val db = buildDb(getApplication())
            db.userDao().updateUser(name, bodMillis, gender)

            refresh()

        }

    }
    fun setGender(gender: Int) {
        userLD.value?.let {
            it.gender = gender
            userLD.value = it // trigger LiveData update
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
