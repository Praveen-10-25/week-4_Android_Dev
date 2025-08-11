package com.example.recipebook.di

import android.content.Context
import androidx.room.Room
import com.example.recipebook.datalayer.dao.RecipeDao
import com.example.recipebook.datalayer.database.RecipeDatabase
import com.example.recipebook.datalayer.repository.LocalRecipeRepository
import com.example.recipebook.datalayer.repository.RecipeRepository
import com.example.recipebook.datalayer.repository.RecipeRepositoryImpl
import com.example.recipebook.domain.Api
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://food2fork.ca/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRecipeDao(db: RecipeDatabase): RecipeDao = db.recipeDao()

    @Provides
    @Singleton
    fun provideLocalRecipeRepository(recipeDao: RecipeDao): LocalRecipeRepository {
        return LocalRecipeRepository(recipeDao)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(api: Api, dao: RecipeDao): RecipeRepository {
        return RecipeRepositoryImpl(api, dao)
    }
}
