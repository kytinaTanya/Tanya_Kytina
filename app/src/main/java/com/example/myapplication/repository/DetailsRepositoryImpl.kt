package com.example.myapplication.repository

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.marks.AddToWatchlistMovie
import com.example.myapplication.models.marks.MarkAsFavouriteMovie
import com.example.myapplication.models.marks.RatingValue
import com.example.myapplication.models.movies.Episode
import com.example.myapplication.models.movies.Film
import com.example.myapplication.models.movies.Person
import com.example.myapplication.models.movies.TV
import com.google.gson.GsonBuilder


class DetailsRepositoryImpl(val service: TmdbService): DetailsRepository {
    override suspend fun getMovieDetails(id: Long): Film {
        val response = service.getMovieDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: Film()
        } else {
            Film()
        }
    }

    override suspend fun getTVDetails(id: Long): TV {
        val response = service.getTvDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: TV()
        } else {
            TV()
        }
    }

    override suspend fun getPersonDetails(id: Long): Person {
        val response = service.getPersonDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: Person()
        } else {
            Person()
        }
    }

    override suspend fun getEpisodeDetails(tvId: Long, seasonNum: Int, episodeNum: Int): Episode {
        val response = service.getEpisodeDetails(tvId = tvId, seasonNum = seasonNum, episodeNum = episodeNum)
        return if (response.isSuccessful) {
            response.body() ?: Episode()
        } else {
            Episode()
        }
    }

    override suspend fun markAsFavorite(id: Long,
                                        type: String,
                                        mark: Boolean,
                                        sessionId: String): PostResponseStatus {
        val response = service.markAsFavourite(sessionId = sessionId,
            body = MarkAsFavouriteMovie(type, id, mark))
        return response.body() ?: PostResponseStatus()
    }

    override suspend fun addToWatchlist(id: Long,
                                        type: String,
                                        add: Boolean,
                                        sessionId: String): PostResponseStatus{
        val response = service.addToWatchlist(sessionId = sessionId,
            body = AddToWatchlistMovie(type, id, add)
        )
        return response.body() ?: PostResponseStatus()
    }

    override suspend fun rateMovie(id: Long, sessionId: String, rating: Float): PostResponseStatus {
        val response = service.rateMovie(id = id, sessionId = sessionId, body = RatingValue(rating))
        return response.body() ?: PostResponseStatus()
    }

    override suspend fun rateTv(id: Long, sessionId: String, rating: Float): PostResponseStatus {
        val response = service.rateTv(id = id, sessionId = sessionId, body = RatingValue(rating))
        return response.body() ?: PostResponseStatus()
    }

    override suspend fun deleteMovieRating(id: Long, sessionId: String) {
        service.deleteMovieRating(id = id, sessionId = sessionId)
    }

    override suspend fun deleteTvRating(id: Long, sessionId: String) {
        service.deleteTvRating(id = id, sessionId = sessionId)
    }

    override suspend fun getMovieAccountStates(id: Long, sessionId: String): AccountStates {
        val response = service.getMovieAccountStates(id = id, sessionId = sessionId)
        val gson = GsonBuilder()
            .registerTypeAdapter(
                AccountStates::class.java,
                AccountStates.AccountStatesDeserializer()
            )
            .create()
        return gson.fromJson(response, AccountStates::class.java)
    }

    override suspend fun getTvAccountStates(id: Long, sessionId: String): AccountStates {
        val response = service.getTvAccountStates(id = id, sessionId = sessionId)
        val gson = GsonBuilder()
            .registerTypeAdapter(
                AccountStates::class.java,
                AccountStates.AccountStatesDeserializer()
            )
            .create()
        return gson.fromJson(response, AccountStates::class.java)
    }
}