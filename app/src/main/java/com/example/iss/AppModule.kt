package com.example.iss

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.iss.api.Api
import com.example.iss.db.AppDatabase
import com.example.iss.db.ISSPositionDao
import com.example.iss.repository.BaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

/**
 * Dependency injection bindings
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getApi(): Api {
        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideBaseRepository(api: Api, issPositionDao: ISSPositionDao) =
        BaseRepository(api, issPositionDao)

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideISSPositionDao(application: Application): ISSPositionDao {
        val db = Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
        return db.issPositionDao()
    }
}