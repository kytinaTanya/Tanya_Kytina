package com.example.myapplication.repository.impl.search_screen

import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.models.pojo.Film
import com.example.myapplication.models.pojo.TV
import com.example.myapplication.repository.repositories.search_screen.RatedItemsRepository
import com.example.myapplication.repository.services.TmdbService
import javax.inject.Inject

class RatedItemsRepositoryImpl @Inject constructor(val service: TmdbService): RatedItemsRepository {

    override suspend fun execute(sessionId: String): RatedItemsRepository.Result {
        val resultMovies = service.ratedMovies(sessionId = sessionId)
        val resultTvs = service.ratedTvs(sessionId = sessionId)
        val resultEpisodes = service.ratedEpisodes(sessionId = sessionId)
        return if (resultMovies.isSuccessful && resultTvs.isSuccessful && resultEpisodes.isSuccessful) {
            RatedItemsRepository.Result.Success(
                movies = resultMovies.body()?.items ?: emptyList(),
                tvs = resultTvs.body()?.items ?: emptyList(),
                episodes = resultEpisodes.body()?.items ?: emptyList()
            )
        } else {
            RatedItemsRepository.Result.ServerError
        }
    }

    private suspend fun getRatedMovies(sessionId: String): List<Film> {
        val response = service.ratedMovies(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            emptyList()
        }
    }

    private suspend fun getRatedTvs(sessionId: String): List<TV> {
        val response = service.ratedTvs(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            emptyList()
        }
    }

    private suspend fun getRatedEpisodes(sessionId: String): List<Episode> {
        val response = service.ratedEpisodes(sessionId = sessionId)
        return if(response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            emptyList()
        }
    }

}