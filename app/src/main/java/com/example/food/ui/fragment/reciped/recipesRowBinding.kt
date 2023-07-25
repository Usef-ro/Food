package com.example.food.ui.fragment.reciped

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.food.R
import com.example.food.domain.model.Result
class recipesRowBinding {

    companion object {

        @BindingAdapter("onRecipesClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLLayou:ConstraintLayout,result:Result){
        recipeRowLLayou.setOnClickListener{
            try{
//                val action=BottomSheetDirections.find/
                recipeRowLLayou.findNavController().navigate(R.id.action_recipedFragment_to_detailActivity)
            }   catch(e:Exception){
                Log.e("recipesRowBinding = > onRecipeClickListener",""+e.message)
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
    }
}