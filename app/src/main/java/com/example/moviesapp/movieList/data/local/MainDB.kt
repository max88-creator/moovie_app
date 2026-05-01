package com.example.moviesapp.movieList.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MainDB: RoomDatabase() {

    abstract val movieDao: MovieDao
}