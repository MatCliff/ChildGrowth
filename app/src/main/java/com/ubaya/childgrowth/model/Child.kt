package com.ubaya.childgrowth.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Child(
    @ColumnInfo(name = "weight")
    var weight: Double,
    @ColumnInfo(name = "height")
    var height: Double,
    @ColumnInfo(name = "age")
    var age: Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}