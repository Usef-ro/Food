package com.example.food.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.food.domain.database.DataStoreRepository
import com.example.food.util.constants.API_KEY
import com.example.food.util.constants.DEFAULT_DIET_TYPE
import com.example.food.util.constants.DEFAULT_MEAL_NUMBER
import com.example.food.util.constants.DEFAULT_MEAL_TYPE
import com.example.food.util.constants.QUERY_API
import com.example.food.util.constants.QUERY_DIET
import com.example.food.util.constants.QUERY_INFORMATION
import com.example.food.util.constants.QUERY_NUMBER
import com.example.food.util.constants.QUERY_SEARCH
import com.example.food.util.constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class recipesViewModel @Inject constructor(
    app: Application,
    val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(app) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE
    var backOnline = false

    var network = false
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    var readBackOnline = dataStoreRepository.readBackOnline.asLiveData()
    fun saveBackOnline(backOnline: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    }

    fun saveMealAndDietType(mealType: String, mealTypedId: Int, diet: String, dietId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypedId, diet, dietId)
        }

    fun applyQueries(): HashMap<String, String> {
        val query = HashMap<String, String>()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectMealsType
                dietType = value.selectDietType
            }
        }

        query[QUERY_NUMBER] = DEFAULT_MEAL_NUMBER
        query[QUERY_TYPE] = dietType
        query[QUERY_DIET] = mealType
        query[QUERY_API] = API_KEY
        query[QUERY_INFORMATION] = "true"

        return query
    }

    fun applySearchQuery(search: String): HashMap<String, String> {
        val query: HashMap<String, String> = HashMap()
        query[QUERY_SEARCH] = search
        query[QUERY_NUMBER] = DEFAULT_MEAL_NUMBER
        query[QUERY_TYPE] = dietType
        query[QUERY_DIET] = mealType
        query[QUERY_API] = API_KEY
        query[QUERY_INFORMATION] = "true"

        return query
    }

    fun showNetworkStatus() {
        if (!network) {

            Toast.makeText(getApplication(), "No Internet", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (network) {
            if (backOnline) {

                Toast.makeText(getApplication(), "We are back to Internet", Toast.LENGTH_SHORT)
                    .show()
                saveBackOnline(false)
            }

        }
    }
}