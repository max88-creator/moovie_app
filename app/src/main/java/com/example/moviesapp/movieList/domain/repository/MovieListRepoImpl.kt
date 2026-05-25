package com.example.moviesapp.movieList.domain.repository

import coil.network.HttpException
import com.example.moviesapp.movieList.data.local.MainDB
import com.example.moviesapp.movieList.data.mappers.toMovie
import com.example.moviesapp.movieList.data.mappers.toMovieEntity
import com.example.moviesapp.movieList.data.remote.MovieApi
import com.example.moviesapp.movieList.domain.model.Movie
import com.example.moviesapp.movieList.util.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MovieListRepoImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDB: MainDB
) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resources<List<Movie>>> {
        return flow {
            emit(Resources.Loading(true))
            val localMovieList = movieDB.movieDao.getMovieByCategory(category)
            val shouldLoadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalMovie) {
                emit(
                    Resources.Success(
                        data = localMovieList
                            .map { movieEntity ->
                                movieEntity.toMovie(category)
                            }
                    )
                )
                emit(Resources.Loading(false))
                return@flow
            }
            val movieListFromApi = try {
                movieApi.getMovieList(
                    category,
                    page
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resources.Error("Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resources.Error("Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resources.Error("Error loading movies"))
                return@flow
            }
            val movieEntities = movieListFromApi.results.let { movieDtos ->
                movieDtos.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }
            movieDB.movieDao.upsertMovieList(movieEntities)
            emit(Resources.Success(movieEntities.map { movieEntity ->
                movieEntity.toMovie(category)
            }
            )
            )
            emit(Resources.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resources<Movie>> {
        return flow {
            emit(Resources.Loading(true))
            val movieEntity = movieDB.movieDao.getMovieById(id)

            if (movieEntity != null) {
                emit(Resources.Success(data = movieEntity.toMovie(movieEntity.category)))
                emit(Resources.Loading(false))
                return@flow
            }
            emit(Resources.Error("Error not such movie"))
            emit(Resources.Loading(false))
        }
    }
}