package com.ubaya.childgrowth.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "bod")
    var bod: Long,
    @ColumnInfo(name = "gender")
    var gender: Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}