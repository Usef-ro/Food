package com.example.food.domain.database

import androidx.room.TypeConverter

import com.example.food.domain.model.foodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class recipesTypeConvert {

    var gson=Gson()


    @TypeConverter
    fun foodRecipeToString(foodRecipe: foodRecipe):String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringTofoodRecipe(data:String):foodRecipe{

        val listType=object:TypeToken<foodRecipe>(){}.type
        return gson.fromJson(data,listType)
    }
}