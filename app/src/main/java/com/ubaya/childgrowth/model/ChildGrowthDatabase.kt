package com.ubaya.childgrowth.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Child::class],version = 1)
abstract class ChildGrowthDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun childDao(): ChildDao

    companion object {
        @Volatile
        private var instance: ChildGrowthDatabase? = null
        private val LOCK = Any()

        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ChildGrowthDatabase::class.java,
            "childgrowthdb"
        ).build()

        operator fun invoke(context: Context){
            if (instance == null) {
                synchronized(LOCK) {
                    instance = buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }
    }
}