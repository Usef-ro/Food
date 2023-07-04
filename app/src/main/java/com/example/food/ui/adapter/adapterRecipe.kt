package com.example.food.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.food.databinding.ItemRecipBinding
import com.example.food.domain.model.foodRecipe
import com.example.food.util.RecipesDiffUtil

class adapterRecipe(

) : RecyclerView.Adapter<adapterRecipe.viewHolder>() {

    var recipe= emptyList<com.example.food.domain.model.Result>()

     class viewHolder(val binding:ItemRecipBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(result: com.example.food.domain.model.Result){
            binding.result=result
            binding.executePendingBindings()
        }

         companion object{
             fun from(parent: ViewGroup):viewHolder{
                 val layoutInflater=
                     LayoutInflater.from(parent.context)
                        val binding=ItemRecipBinding.inflate(layoutInflater,parent,false)
                 return viewHolder(binding)
             }
         }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterRecipe.viewHolder {
        return viewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: adapterRecipe.viewHolder, position: Int) {
       val currentResult=recipe[position]

        holder.bind(currentResult)

    }

    override fun getItemCount(): Int {
       return recipe.size
    }

    fun setData(newData:foodRecipe){
        val recipesDiffUtil=RecipesDiffUtil(recipe,newData.result)
        val diffUtilResult=DiffUtil.calculateDiff(recipesDiffUtil)

        recipe=newData.result
        diffUtilResult.dispatchUpdatesTo(this)
    }

}