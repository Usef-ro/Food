package com.example.food.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.food.domain.Repository
import com.example.food.domain.database.recipesEntity
import com.example.food.domain.model.foodRecipe
import com.example.food.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(
    private val repository: Repository
    , app:Application) :AndroidViewModel(app) {

    val readRecipes:LiveData<List<recipesEntity>> =repository.local.readDatabase().asLiveData(  )


    fun insertRecipes(recipesEntity: recipesEntity)=
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    var recipesResponse:MutableLiveData<NetworkResult<foodRecipe>> = MutableLiveData()
    @RequiresApi(Build.VERSION_CODES.M)
    fun getRecipes(queryMap: Map<String,String>)=viewModelScope.launch {
        getRecipesSafeCall(queryMap)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun getRecipesSafeCall(queryMap: Map<String, String>) {

        recipesResponse.value=NetworkResult.Loading()

        if(hasInternetConnection()){

            try{
                val response=repository.remote.getRecipes(queryMap)
                recipesResponse.value=handleFoodRecipesResponse(response)

                val foodRecipe=recipesResponse.value!!.data
                if(foodRecipe!=null){
                    offlineCacheRecipes(foodRecipe)
                }
            }catch (e:Exception){
                Log.e("getRecipesSafeCall ",""+e.message)
                recipesResponse.value=NetworkResult.Error("Recipes not Found")
            }
        }else{

            recipesResponse.value=NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: foodRecipe) {

        val recipesEntity=recipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(repository: retrofit2.Response<foodRecipe>): NetworkResult<foodRecipe> {

        when{
            repository.message().toString().contains("timeout")->{
                return NetworkResult.Error("Timeout")
            }
            repository.code()==402->{
                return NetworkResult.Error("API Key Limited.")
            }
            repository.body()!!.result.isEmpty()->{
                return NetworkResult.Error("Recipes not found")
            }
            repository.isSuccessful->{
                val foodRecipe=repository.body()
                return NetworkResult.Success(foodRecipe)
            }
            else->{
                return NetworkResult.Error(repository.message())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun hasInternetConnection():Boolean{
        val connectivity=getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork=connectivity.activeNetwork?:return false

        val capabilities=connectivity.getNetworkCapabilities(activeNetwork)?:return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    capabilities . hasTransport (NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }
    }

}