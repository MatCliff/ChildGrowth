package com.ubaya.childgrowth.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChildDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg child: Child)

    @Query("SELECT * FROM child")
    fun selectAllChild(): List<Child>
}