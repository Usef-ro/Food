package com.example.food.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.food.app

class viewmodelFactory(app:app) : ViewModelProvider.AndroidViewModelFactory(app){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }
}