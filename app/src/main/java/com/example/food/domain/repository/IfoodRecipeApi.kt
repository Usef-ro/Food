package com.example.food.domain.repository

import com.example.food.domain.model.foodRecipe
import com.example.food.util.constants.RICEPS_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface IfoodRecipeApi {

    @GET(RICEPS_URL)
    suspend fun getRecpes(
            @QueryMap queries:Map<String,String>
    ): Response<foodRecipe>

}