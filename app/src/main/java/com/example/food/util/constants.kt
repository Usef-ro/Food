package com.example.food.util

object constants {


    const val BASE_URL="https://api.spoonacular.com/"
    const val RICEPS_URL="/recipes/complexSearch"
    const val API_KEY="9d6d4299545d4f239b3585cd578ca39c"

    const val DATABASE_NAME="recipes_database"
    const val RECIPES_TABLE="recipes_table"

    fun que():HashMap<String,String>{
        val query= HashMap<String, String>()

        query["number"]="50"
        query["apiKey"]= API_KEY
        query["type"]="finger"
        query["diet"]="vegan"
        query["addRecipeInformation"]="true"

        return query
    }


}