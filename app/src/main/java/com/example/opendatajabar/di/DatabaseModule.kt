package com.example.openjabar.di

import android.content.Context
import androidx.room.Room
import com.example.opendatajabar.data.AppDatabase
import com.example.opendatajabar.data.RegionDao
import com.example.opendatajabar.data.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "open_data_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideRegionDao(db: AppDatabase): RegionDao = db.regionDao()
}
