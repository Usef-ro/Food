package com.example.food.domain.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [recipesEntity::class],
    version = 1, exportSchema = false
)

@TypeConverters(recipesTypeConvert::class)
abstract  class recipesDataBase :RoomDatabase(){

    abstract fun recipesDao():recipesDao

}