package com.ubaya.childgrowth.model

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun selectAllUser(): List<User>

    @Query("UPDATE user SET name=:name, bod=:bod, gender=:gender WHERE uuid=:id")
    fun updateUser(name:String, bod:Long, gender:Int, id:Int)
}