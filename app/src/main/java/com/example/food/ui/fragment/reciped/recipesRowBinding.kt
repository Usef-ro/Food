package com.example.food.ui.fragment.reciped

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.food.R
import com.example.food.R.id.action_recipedFragment_to_detailActivity
import com.example.food.domain.model.Result
import org.jsoup.select.Evaluator

class recipesRowBinding {

    companion object {
/*
item click
 */

        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        @BindingAdapter("onRecipesClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result: Result) {
            Log.e("onRecipeClickListener", "" + result)
            recipeRowLayout.setOnClickListener {
                try {

//                    val action=recipedFragmentDirections.actionRecipedFragmentToDetailActivity(result)
//                    val a=Bundle()
//                    a.putParcelable("a",result)
                    val bundle = bundleOf("a" to result)
                    recipeRowLayout.findNavController()
                        .navigate(R.id.action_recipedFragment_to_detailActivity,bundle)

                } catch (e: Exception) {
                    Log.e("recipesRowBinding = > onRecipeClickListener", "" + e.message)
                }
            }
        }


        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImage(imageView: ImageView, url: String) {
            imageView.load(url) {
                crossfade(600)
                error(R.drawable.ic_launcher_foreground)
                placeholder(R.drawable.ic_launcher_foreground)
            }
        }

        @BindingAdapter("setNumberofLikes")
        @JvmStatic
        fun setNumberofLikes(txt: TextView, likes: Int) {
            txt.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, min: Int) {
            textView.text = min.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.green))
                    }

                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }

        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
//        Json.parse(textView)
                textView.text = description

            }
        }
    }


}