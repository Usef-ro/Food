package com.example.food.domain.remote

import android.util.Log
import com.example.food.domain.model.foodRecipe
import com.example.food.domain.repository.IfoodRecipeApi
import retrofit2.Response
import javax.inject.Inject

class RemoteDataResource @Inject constructor(
    val iFood: IfoodRecipeApi
) {

    suspend fun getRecipes(queryMap: Map<String, String>): Response<foodRecipe> {
        Log.e("RemoteDataResource ", "" + iFood.getRecpes(queryMap).body())
        Log.e("RemoteDataResource ", "" + iFood.getRecpes(queryMap))
        return iFood.getRecpes(queryMap)
    }


    suspend fun searchRecipes(queryMap: Map<String, String>): Response<foodRecipe> {
        return iFood.searchRecipes(queryMap)
    }
}