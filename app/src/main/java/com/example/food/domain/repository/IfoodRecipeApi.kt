package com.example.food.domain.repository

import com.example.food.domain.model.foodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface IfoodRecipeApi {

    @GET("recipes/716429/information")
    suspend fun getRecpes(
            @QueryMap queries:Map<String,String>
    ): Response<foodRecipe>

}