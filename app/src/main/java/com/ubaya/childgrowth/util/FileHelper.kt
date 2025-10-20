package com.ubaya.childgrowth.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileHelper(val context: Context) {
    val folderName = "measure_data"
    val fileName = "data.txt"

    //BUAT FOLDER DAN FILE
    private fun getFile(): File{
        val dir = File(context.filesDir, folderName)
        if(!dir.exists()){
            dir.mkdir()
        }
        return File(dir, fileName)
    }

    //TAMBAH DATA
    fun addToFile(data: String){
        try {
            val file = getFile()
            FileOutputStream(file, true).use{
                output -> output.write((data + "\n").toByteArray())
            }
        }catch (e: IOException){
            e.printStackTrace().toString()
        }
    }

    //BACA DATA
    fun readFromFile(): String{
        return try {
            val file = getFile()
            if(!file.exists()){
                return "No data available"
            }
            file.bufferedReader().use { it.readText() }
        }catch (e: IOException){
            e.printStackTrace().toString()
        }
    }

    fun getFIlePath(): String{
        return getFile().absolutePath
    }
}