package com.example.food.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.food.util.constants
import com.example.food.util.constants.que

class recipesViewModel(app:Application):AndroidViewModel(app)
{
    fun applyQueries():HashMap<String,String>{
        return que()
    }

}