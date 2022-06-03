package com.example.myapplication.repository.impl

import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.MoviesResponse
import com.example.myapplication.models.pojo.Person
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.repository.repositories.Repository
import com.example.myapplication.repository.services.TmdbService
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val service: TmdbService) : Repository {

    override suspend fun getListOfPopularMovies() : Repository.Result {
        return try {
            val response = service.getPopularMovies()
            handleFilmResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfNowPlayingMovies(): Repository.Result {
        return try {
            val response = service.getNowPlayingMovies()
            return handleFilmResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfUpcomingMovies(): Repository.Result {
        return try {
            val response = service.getUpcomingMovies()
            return handleFilmResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfTopRatedMovies(): Repository.Result {
        return try {
            val response = service.getTopRatedMovies()
            return handleFilmResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfPopularTv(): Repository.Result {
        return try {
            val response = service.getPopularTV()
            return handleTVResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfOnAirTodayTV(): Repository.Result {
        return try {
            val response = service.getOnAirTodayTV()
            return handleTVResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfNowOnAirTV(): Repository.Result {
        return try {
            val response = service.getNowOnAirTV()
            return handleTVResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfTopRatedTV(): Repository.Result {
        return try {
            val response = service.getTopRatedTV()
            return handleTVResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    override suspend fun getListOfPopularPersons(): Repository.Result {
        return try {
            val response = service.getPopularPersons()
            return handlePersonResult(resultBody = response.body(), isSuccessful = response.isSuccessful)
        } catch (e: ConnectException) {
            Repository.Result.Error
        } catch (e: UnknownHostException) {
            Repository.Result.Error
        } catch (e: SocketTimeoutException) {
            Repository.Result.Error
        }
    }

    companion object {
        const val EMPTY_STRING = ""
    }

    private fun handleFilmResult(resultBody: MoviesResponse<Film>?, isSuccessful: Boolean): Repository.Result {
        return if (isSuccessful) {
            if (resultBody?.items != null) {
                Repository.Result.Success.FilmSuccess(resultBody.items)
            } else {
                Repository.Result.Error
            }
        } else {
            Repository.Result.Error
        }
    }

    private fun handleTVResult(resultBody: MoviesResponse<TV>?, isSuccessful: Boolean): Repository.Result {
        return if (isSuccessful) {
            if (resultBody?.items != null) {
                Repository.Result.Success.TvSuccess(resultBody.items)
            } else {
                Repository.Result.Error
            }
        } else {
            Repository.Result.Error
        }
    }

    private fun handlePersonResult(resultBody: MoviesResponse<Person>?, isSuccessful: Boolean): Repository.Result {
        return if (isSuccessful) {
            if (resultBody?.items != null) {
                Repository.Result.Success.PersonSuccess(resultBody.items)
            } else {
                Repository.Result.Error
            }
        } else {
            Repository.Result.Error
        }
    }
}