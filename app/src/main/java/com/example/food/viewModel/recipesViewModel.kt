package com.example.food.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.food.util.constants
import com.example.food.util.constants.DEFAULT_DIET_TYPE
import com.example.food.util.constants.DEFAULT_MEAL_NUMBER
import com.example.food.util.constants.DEFAULT_MEAL_TYPE
import com.example.food.util.constants.QUERY_API
import com.example.food.util.constants.QUERY_DIET
import com.example.food.util.constants.QUERY_INFORMATION
import com.example.food.util.constants.QUERY_NUMBER
import com.example.food.util.constants.QUERY_TYPE


class recipesViewModel(app:Application):AndroidViewModel(app)
{
    fun applyQueries():HashMap<String,String>{
        val query= HashMap<String, String>()

        query[QUERY_NUMBER]=DEFAULT_MEAL_NUMBER
        query[QUERY_TYPE]= constants.API_KEY
        query[QUERY_DIET]=DEFAULT_MEAL_TYPE
        query[QUERY_API]=DEFAULT_DIET_TYPE
        query[QUERY_INFORMATION]="true"

        return query
    }

}