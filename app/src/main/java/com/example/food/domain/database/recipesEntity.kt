package com.example.food.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.food.domain.model.foodRecipe
import com.example.food.util.constants.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class recipesEntity(
    var foodRecipe: foodRecipe
) {
    @PrimaryKey(autoGenerate = false)

    var id: Int = 0
}