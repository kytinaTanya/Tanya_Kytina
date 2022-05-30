package com.example.myapplication.repository.impl.search_screen

import com.example.myapplication.repository.repositories.search_screen.RatedItemsRepository
import com.example.myapplication.repository.services.TmdbService
import java.net.ConnectException
import javax.inject.Inject

class RatedItemsRepositoryImpl @Inject constructor(val service: TmdbService): RatedItemsRepository {

    override suspend fun executeLoadingRatedFilms(sessionId: String): RatedItemsRepository.Result {
        return try {
            val resultMovies = service.ratedMovies(sessionId = sessionId)
            if (resultMovies.isSuccessful && resultMovies.body() != null) {
                RatedItemsRepository.Result.Success.FilmSuccess(resultMovies.body()!!.items)
            } else {
                RatedItemsRepository.Result.ServerError
            }
        } catch (e: ConnectException) {
            RatedItemsRepository.Result.ServerError
        }
    }

    override suspend fun executeLoadingRatedTvs(sessionId: String): RatedItemsRepository.Result {
        return try {
            val resultTvs = service.ratedTvs(sessionId = sessionId)
            if (resultTvs.isSuccessful && resultTvs.body() != null) {
                RatedItemsRepository.Result.Success.TvSuccess(resultTvs.body()!!.items)
            } else {
                RatedItemsRepository.Result.ServerError
            }
        } catch (e: ConnectException) {
            RatedItemsRepository.Result.ServerError
        }
    }

    override suspend fun executeLoadingRatedEpisodes(sessionId: String): RatedItemsRepository.Result {
        return try {
            val resultEpisodes = service.ratedEpisodes(sessionId = sessionId)
            if (resultEpisodes.isSuccessful && resultEpisodes.body() != null) {
                RatedItemsRepository.Result.Success.EpisodesSuccess(resultEpisodes.body()!!.items)
            } else {
                RatedItemsRepository.Result.ServerError
            }
        } catch (e: ConnectException) {
            RatedItemsRepository.Result.ServerError
        }
    }
}