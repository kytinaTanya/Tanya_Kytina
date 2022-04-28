package com.example.myapplication.repository.impl.search_screen

import com.example.myapplication.repository.repositories.search_screen.SearchRepository
import com.example.myapplication.repository.services.TmdbService

class SearchRepositoryImpl(val service: TmdbService): SearchRepository {

    override suspend fun execute(query: String): SearchRepository.Result {
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
    }
}