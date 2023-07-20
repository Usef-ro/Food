package com.example.food.di

import android.content.Context
import androidx.room.Room
import com.example.food.domain.database.recipesDataBase
import com.example.food.util.constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        recipesDataBase::class.java, DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideDao(dataBase: recipesDataBase) = dataBase.recipesDao()

}