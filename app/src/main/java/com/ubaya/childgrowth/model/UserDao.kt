package com.ubaya.childgrowth.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): User?

    @Insert
    fun insertUser(user: User)

    @Query("UPDATE user SET name=:name, bod=:bod, gender=:gender")
    fun updateUser(name:String, bod:Long, gender:Int)
}