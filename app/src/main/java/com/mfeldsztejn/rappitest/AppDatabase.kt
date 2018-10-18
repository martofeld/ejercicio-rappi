package com.mfeldsztejn.rappitest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mfeldsztejn.rappitest.dtos.Configuration
import com.mfeldsztejn.rappitest.dtos.Genre
import com.mfeldsztejn.rappitest.dtos.room.ConfigurationDao
import com.mfeldsztejn.rappitest.dtos.room.GenreDao
import com.mfeldsztejn.rappitest.dtos.room.ImagesConverter
import com.mfeldsztejn.rappitest.dtos.room.ListConverter
import java.util.concurrent.Executors

@Database(entities = [Genre::class, Configuration::class], version = 1, exportSchema = false)
@TypeConverters(ImagesConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun configurationDao(): ConfigurationDao

    companion object {
        private const val DATABASE_NAME = "database"
        private var instance: AppDatabase? = null

        fun init(context: Context) {
            if (instance == null) {
                synchronized(AppDatabase::class.java) {
                    if (instance == null) {
                        instance = Room
                                .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                                .setQueryExecutor(Executors.newSingleThreadExecutor())
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
        }

        fun getInstance(): AppDatabase {
            return instance!!
        }
    }

}