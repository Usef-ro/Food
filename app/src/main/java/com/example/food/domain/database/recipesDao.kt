package com.example.food.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.food.util.constants.RECIPES_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface recipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: recipesEntity)

    @Query("SELECT * FROM ${RECIPES_TABLE} ORDER BY id ASC")
    fun readRecipes(): Flow<List<recipesEntity>>
}