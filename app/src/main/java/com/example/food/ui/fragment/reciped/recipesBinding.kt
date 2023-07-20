package com.example.food.ui.fragment.reciped

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.food.domain.database.recipesEntity
import com.example.food.domain.model.foodRecipe
import com.example.food.util.NetworkResult

class recipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisible(
            imageView: ImageView,
            apiRes: NetworkResult<foodRecipe>?,
            database: List<recipesEntity>?
        ) {

            if (apiRes is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiRes is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE
            } else if (apiRes is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            }
        }


        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiRes: NetworkResult<foodRecipe?>,
            database: List<recipesEntity>?
        ) {
            if (apiRes is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiRes.message.toString()
            } else if (apiRes is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else if (apiRes is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }
        }


        // TEMPORARLLY
        @BindingAdapter("readApiResponse3", "readDatabase3", requireAll = true)
        @JvmStatic
        fun errorRecy(
            view: View,
            apiRes: NetworkResult<foodRecipe?>,
            database: List<recipesEntity>?
        ) {
            if (apiRes is NetworkResult.Error && database.isNullOrEmpty()) {
                view.visibility = View.INVISIBLE
            }
        }

    }

}
