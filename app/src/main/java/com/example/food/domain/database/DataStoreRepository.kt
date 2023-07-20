package com.example.food.domain.database


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.food.util.constants.DEFAULT_DIET_TYPE
import com.example.food.util.constants.DEFAULT_MEAL_TYPE
import com.example.food.util.constants.NAME_PREFERENCE
import com.example.food.util.constants.PREFERENCE_DIET_TYPE
import com.example.food.util.constants.PREFERENCE_DIET_TYPE_ID
import com.example.food.util.constants.PREFERENCE_MEAL_TYPE
import com.example.food.util.constants.PREFERENCE_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext val context: Context) {


    // PreferenceKeys
    object PreferenceKeys {
        val selectMealsType = preferencesKey<String>(PREFERENCE_MEAL_TYPE)
        val selectMealsTypeId = preferencesKey<Int>(PREFERENCE_MEAL_TYPE_ID)
        val selectDietType = preferencesKey<String>(PREFERENCE_DIET_TYPE)
        val selectDieTypeId = preferencesKey<Int>(PREFERENCE_DIET_TYPE_ID)

    }

    // Create DataStore
    val dataStore: DataStore<Preferences> = context.createDataStore(
        name = NAME_PREFERENCE
    )

    // Save
    suspend fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dieTypeId: Int
    ) {
        dataStore.edit { pref ->
            pref[PreferenceKeys.selectMealsType] = mealType
            pref[PreferenceKeys.selectMealsTypeId] = mealTypeId
            pref[PreferenceKeys.selectDietType] = dietType
            pref[PreferenceKeys.selectDieTypeId] = dieTypeId
        }
    }


    val readMealAndDietType: Flow<mealAndDietType> = dataStore.data.catch { except ->
        if (except is IOException) {
            emit(emptyPreferences())
        } else {
            throw except
        }
    }
        .map { pref ->
            val selectedMealType = pref[PreferenceKeys.selectMealsType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = pref[PreferenceKeys.selectMealsTypeId] ?: 0
            val selectedDietType = pref[PreferenceKeys.selectDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = pref[PreferenceKeys.selectDieTypeId] ?: 0
            mealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }
}

data class mealAndDietType(
    val selectMealsType: String,
    val selectMealsTypeId: Int,
    val selectDietType: String,
    val selectDieTypeId: Int,
)