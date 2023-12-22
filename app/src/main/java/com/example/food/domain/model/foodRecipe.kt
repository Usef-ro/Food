package com.example.food.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.annotation.Nullable


data class foodRecipe(
    val number: Int,
    val offset: Int,
    @SerializedName("results")
    val result: List<Result>,
    val totalResults: Int
)


@Parcelize
data class AnalyzedInstruction(
    @SerializedName("name")
    val name: String,
    @SerializedName("steps")
    val steps: List<Step>
) : Parcelable

@Parcelize
data class Step(
    @SerializedName("equipment")
    val equipment: List<Equipment>,
    @SerializedName("ingredients")
    val ingredients: List<Ingredient>,
    @SerializedName("length")
    val length: Length,
    @SerializedName("number")
    val number: Int,
    @SerializedName("step")
    val step: String
) : Parcelable

@Parcelize
data class Equipment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("localizedName")
    val localizedName: String,
    @SerializedName("name")
    val name: String
) : Parcelable

@Parcelize
data class Ingredient(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("localizedName")
    val localizedName: String,
    @SerializedName("name")
    val name: String
) : Parcelable

@Parcelize
data class Length(
    @SerializedName("number")
    val number: Int,
    @SerializedName("unit")
    val unit: String
) : Parcelable