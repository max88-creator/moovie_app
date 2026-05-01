package com.example.moviesapp.movieList.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)
    @Query("SELECT * FROM movie_ent WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity
    @Query("SELECT * FROM movie_ent WHERE category = :category")
    suspend fun getMovieByCategory(category: String): List<MovieEntity>
}