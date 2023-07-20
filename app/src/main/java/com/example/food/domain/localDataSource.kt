package com.example.food.domain

import com.example.food.domain.database.recipesDao
import com.example.food.domain.database.recipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class localDataSource @Inject constructor(
    val recipesDao: recipesDao
) {

    fun readDatabase(): Flow<List<recipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: recipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}