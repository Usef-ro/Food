package com.example.food.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

import kotlinx.parcelize.RawValue
import java.io.Serializable
import javax.annotation.Nullable

//@Parcelize
@Parcelize
@Nullable
data class Result(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int?=null,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: @RawValue List<AnalyzedInstruction>,
    @SerializedName("cheap")
    val cheap: Boolean,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int?=null,
    @SerializedName("creditsText")
    val creditsText: String,
    @SerializedName("cuisines")
    val cuisines: @RawValue List<Any>,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("diets")
    val diets: List<String>,
    @SerializedName("dishTypes")
    val dishTypes: List<String>,
    @SerializedName("gaps")
    val gaps: String,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @SerializedName("healthScore")
    val healthScore: Int?=null,
    @SerializedName("id")
    val id: Int?=null,
    @SerializedName("image")
    val image: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean,
    @SerializedName("occasions")
    val occasions: @RawValue List<Any>,
    @SerializedName("preparationMinutes")
    val preparationMinutes: Int?=null,
    @SerializedName("pricePerServing")
    val pricePerServing: Double,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?=null,
    @SerializedName("servings")
    val servings: Int?=null,
    @SerializedName("sourceName")
    val sourceName: String,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("sustainable")
    val sustainable: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean,
    @SerializedName("veryPopular")
    val veryPopular: Boolean,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?=null
) : Parcelable,Serializable

