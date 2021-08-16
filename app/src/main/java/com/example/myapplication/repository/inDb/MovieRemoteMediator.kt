package com.example.myapplication.repository.inDb

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.room.dao.MovieDao
import com.example.myapplication.room.dao.PageDao
import com.example.myapplication.room.db.MovieDb
import com.example.myapplication.room.entity.PageEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(val db: MovieDb, val tmdbService: TmdbService) : RemoteMediator<Int, Movie>() {
    private val movieDao: MovieDao = db.moviesDao()
    private val pageDao: PageDao = db.keysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        pageDao.remoteKey(1)
                    }
                    if(remoteKey.page == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.page
                }
            }

            val data = tmdbService.getTopRatedMovies(language = "ru-RU", page = loadKey ?: 1).body()

            val items = data?.movies?.map { it.copy()}

            db.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                    pageDao.delete()
                }

                items?.let { movieDao.insertAll(it) }
                data?.page?.let { PageEntity(it) }?.let { pageDao.insert(it) }
            }

            return MediatorResult.Success(endOfPaginationReached = items?.isEmpty() ?: true)
        } catch(e: IOException) {
            return MediatorResult.Error(e)
        } catch(e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}