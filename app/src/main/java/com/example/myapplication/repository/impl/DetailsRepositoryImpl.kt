package com.example.myapplication.repository.impl

import android.media.Image
import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.marks.AddToWatchlistMovie
import com.example.myapplication.models.marks.MarkAsFavouriteMovie
import com.example.myapplication.models.marks.RatingValue
import com.example.myapplication.models.movies.*
import com.example.myapplication.repository.repositories.DetailsRepository
import com.example.myapplication.repository.services.TmdbService
import com.google.gson.GsonBuilder


class DetailsRepositoryImpl(val service: TmdbService): DetailsRepository {
    override suspend fun getMovieDetails(id: Long): FilmDetails {
        val response = service.getMovieDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: FilmDetails()
        } else {
            FilmDetails()
        }
    }

    override suspend fun getMoviesImages(id: Long): ImageData {
        val response = service.getMovieImages(id = id)
        return if(response.isSuccessful) {
            response.body() ?: ImageData()
        } else {
            ImageData()
        }
    }

    override suspend fun getMoviesCredits(id: Long): CreditsResponse {
        val response = service.getMovieCredits(id = id)
        return if(response.isSuccessful) {
            response.body() ?: CreditsResponse()
        } else {
            CreditsResponse()
        }
    }

    override suspend fun getMovieVideos(id: Long): List<VideoResult> {
        val response = service.getMovieVideos(id = id)
        return if(response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getRecommendationMovies(id: Long): List<Film> {
        val response = service.getRecommendationMovies(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    override suspend fun getSimilarMovies(id: Long): List<Film> {
        val response = service.getSimilarMovies(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    override suspend fun getTVDetails(id: Long): TvDetails {
        val response = service.getTvDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            val responseBody = response.body()
            if(responseBody != null) {
                val seasons: MutableList<Season> = arrayListOf()
                responseBody.seasons.forEach {
                    it.showId = responseBody.id
                    seasons.add(it)
                }
                responseBody.seasons = seasons
                responseBody.lastEpisode.showId = responseBody.id
                responseBody
            } else {
                TvDetails()
            }
        } else {
            TvDetails()
        }
    }

    override suspend fun getTvsImages(id: Long): ImageData {
        val response = service.getTvImages(id = id)
        return if(response.isSuccessful) {
            response.body() ?: ImageData()
        } else {
            ImageData()
        }
    }

    override suspend fun getTvsCredits(id: Long): CreditsResponse {
        val response = service.getTvCredits(id = id)
        return if(response.isSuccessful) {
            response.body() ?: CreditsResponse()
        } else {
            CreditsResponse()
        }
    }

    override suspend fun getTvVideos(id: Long): List<VideoResult> {
        val response = service.getTvVideos(id = id)
        return if(response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getRecommendationTvs(id: Long): List<TV> {
        val response = service.getRecommendationTvs(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    override suspend fun getSimilarTvs(id: Long): List<TV> {
        val response = service.getSimilarTvs(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    override suspend fun getPersonDetails(id: Long): PersonDetails {
        val response = service.getPersonDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: PersonDetails()
        } else {
            PersonDetails()
        }
    }

    override suspend fun getPersonsImages(id: Long): List<ImageUrlPath> {
        val response = service.getPersonImages(id = id)
        return if(response.isSuccessful) {
            response.body()?.profiles ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun getSeasonDetails(tvId: Long, seasonNum: Int): SeasonDetails {
        val response = service.getSeasonDetails(tvId = tvId, seasonNum = seasonNum)
        return if(response.isSuccessful) {
            val responseBody = response.body()
            if(responseBody != null) {
                val episodes: MutableList<Episode> = arrayListOf()
                responseBody.episodes.forEach {
                    it.showId = tvId
                    episodes.add(it)
                }
                responseBody.episodes = episodes
                responseBody
            } else {
                SeasonDetails()
            }
        } else {
            SeasonDetails()
        }
    }

    override suspend fun getEpisodeDetails(tvId: Long, seasonNum: Int, episodeNum: Int): EpisodeDetails {
        val response = service.getEpisodeDetails(tvId = tvId, seasonNum = seasonNum, episodeNum = episodeNum)
        return if (response.isSuccessful) {
            response.body() ?: EpisodeDetails()
        } else {
            EpisodeDetails()
        }
    }

    override suspend fun getCollectionsDetails(id: Int): MovieCollection {
        val response = service.getCollectionDetails(id)
        return if(response.isSuccessful) {
            response.body() ?: MovieCollection(0, "", "", "", emptyList())
        } else {
            MovieCollection(0, "", "", "", emptyList())
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