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
        loadingLD.value = true 			// progress bar start muncul
        dataLoadErrorLD.value = false  	// tidak ada error

        val childList = ArrayList<Child>()
        try {
            val fileContent = fileHelper.readFromFile()

            if (fileContent == "No data available") {
                // File is empty or doesn't exist, post an empty list
                dataLoadErrorLD.value = true
                loadingLD.value = false
            } else {
                // Split the file content by newlines
                val lines = fileContent.lines()

                for (line in lines) {
                    // Ensure the line is not blank before trying to parse
                    if (line.isNotBlank()) {
                        try {
                            // Parse each line as a *single* Child object
                            val child = gson.fromJson(line, Child::class.java)
                            dataLoadErrorLD.value = false
                            childList.add(child)
                        } catch (e: JsonSyntaxException) {
                            // This catch is important in case one line is corrupted
                            Log.e("ListViewModel", "Failed to parse line: $line", e)
                            dataLoadErrorLD.postValue(true)
                        }
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

    fun testSaveFile() {
        val testfileHelper = FileHelper(getApplication())
        testfileHelper.addToFile("Hello, world!")
        val content = testfileHelper.readFromFile()
        Log.d("print_file", content)
        Log.d("print_file", testfileHelper.getFilePath())
    }
    override fun onCleared() {
        super.onCleared()
    }

}