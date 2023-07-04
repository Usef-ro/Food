package com.example.food.domain.remote

import com.example.food.domain.model.foodRecipe
import com.example.food.domain.repository.IfoodRecipeApi
import retrofit2.Response
import retrofit2.http.QueryMap
import javax.inject.Inject

class RemoteDataResource @Inject constructor(
    val iFood: IfoodRecipeApi
) {

    suspend fun getRecipes(queryMap: Map<String,String>):Response<foodRecipe>{
       return iFood.getRecpes(queryMap)
    }
}