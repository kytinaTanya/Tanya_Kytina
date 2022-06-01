package com.example.myapplication.repository.impl.search_screen

import com.example.myapplication.repository.repositories.search_screen.SearchRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchRepositoryImpl(val service: TmdbService): SearchRepository {

    override suspend fun execute(query: String): SearchRepository.Result {
        return try {
            val movieResult = service.searchMovies(query = query)
            val tvResult = service.searchTvs(query = query)
            return if (movieResult.isSuccessful && tvResult.isSuccessful) {
                val movies = movieResult.body()?.items
                val tvs = tvResult.body()?.items
                if (movies == null && tvs == null) {
                    SearchRepository.Result.ResultError
                } else {
                    SearchRepository.Result.Success(
                        movies = movies ?: emptyList(),
                        tvs = tvs ?: emptyList()
                    )
                }
            } else {
                SearchRepository.Result.ServerError
            }
        } catch (e: HttpException) {
            SearchRepository.Result.ServerError
        } catch (e: ConnectException) {
            SearchRepository.Result.ServerError
        } catch (e: UnknownHostException) {
            SearchRepository.Result.ServerError
        } catch (e: SocketTimeoutException) {
            SearchRepository.Result.ServerError
        }
    }
}