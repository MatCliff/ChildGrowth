package com.ubaya.childgrowth.util

import android.content.Context
import com.ubaya.childgrowth.model.ChildGrowthDatabase


val DB_NAME = "childgrowthdb"

fun buildDb(context: Context): ChildGrowthDatabase {
    val db = ChildGrowthDatabase.buildDatabase(context)
    return db
}