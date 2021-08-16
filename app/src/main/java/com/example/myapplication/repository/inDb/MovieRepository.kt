package com.example.myapplication.repository.inDb

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.Repository
import com.example.myapplication.room.db.MovieDb
import kotlinx.coroutines.flow.Flow

class MovieRepository(val db: MovieDb, val tmdbService: TmdbService) : Repository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getData(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(20, 5),
        remoteMediator = MovieRemoteMediator(db, tmdbService)
    ){
        db.moviesDao().pagingSource(10.0F)
    }.flow
}