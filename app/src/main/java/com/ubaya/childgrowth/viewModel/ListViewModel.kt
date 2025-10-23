package com.ubaya.childgrowth.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.ubaya.childgrowth.model.Child
import com.ubaya.childgrowth.util.FileHelper

class ListViewModel(application: Application): AndroidViewModel(application) {
    val growthLD = MutableLiveData<ArrayList<Child>>()
    val dataLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val fileHelper = FileHelper(getApplication())
    private val gson = Gson()

    fun refresh() {
        loadingLD.value = true 			// progress bar muncul
        dataLoadErrorLD.value = false  	// tidak ada error

        val childList = ArrayList<Child>()
        try {
            val fileContent = fileHelper.readFromFile()

            if (fileContent == "No data available") {
                dataLoadErrorLD.value = true
                loadingLD.value = false
            } else {
                val lines = fileContent.lines()

                for (line in lines) {
                    try {
                        val child = gson.fromJson(line, Child::class.java)
                        dataLoadErrorLD.value = false
                        if (child != null) {
                            childList.add(child)
                        }
                    } catch (e: JsonSyntaxException) {
                        Log.e("ListViewModel", "Failed to parse line: $line", e)
                        dataLoadErrorLD.postValue(true)
                    }
                }

                growthLD.value = childList
                loadingLD.value = false

            }
        } catch (e: Exception) {
            Log.e("ListViewModel", "Error reading file", e)
            dataLoadErrorLD.value = true
            loadingLD.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}