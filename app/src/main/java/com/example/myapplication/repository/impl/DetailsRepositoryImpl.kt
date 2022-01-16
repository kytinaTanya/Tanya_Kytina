package com.example.myapplication.repository.impl

import com.example.myapplication.models.history.PostResponseStatus
import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.marks.AddToWatchlistMovie
import com.example.myapplication.models.marks.MarkAsFavouriteMovie
import com.example.myapplication.models.marks.RatingValue
import com.example.myapplication.models.pojo.*
import com.example.myapplication.models.pojo.view.MovieView
import com.example.myapplication.models.pojo.view.PersonView
import com.example.myapplication.models.pojo.view.TvView
import com.example.myapplication.repository.repositories.DetailsRepository
import com.example.myapplication.repository.services.TmdbService
import com.google.gson.GsonBuilder


class DetailsRepositoryImpl(val service: TmdbService): DetailsRepository {
    override suspend fun getMovieView(id: Long, sessionId: String): MovieView {
        val details = getMovieDetails(id)
        val images = getMoviesImages(id)
        val credits = getMoviesCredits(id)
        val states = getMovieAccountStates(id, sessionId)
        return MovieView(
            adult = details.adult,
            collection = details.collection,
            budget = details.budget,
            genres = details.genres,
            homepage = details.homepage,
            id = details.id,
            originalLanguage = details.originalLanguage,
            originalTitle = details.originalTitle,
            overview = details.overview,
            popularity = details.popularity,
            posterPath = details.posterPath,
            companies = details.companies,
            countries = details.countries,
            releaseDate = details.releaseDate,
            revenue = details.revenue,
            runtime = details.runtime,
            languages = details.languages,
            status = details.status,
            tagline = details.tagline,
            title = details.title,
            rating = details.rating,
            average = details.average,
            backdrops = images.backdrops,
            posters = images.posters,
            cast = credits.cast,
            crew = credits.crew,
            trailers = getMovieVideos(id),
            recommendations = getRecommendationMovies(id),
            similar = getSimilarMovies(id),
            favorite = states.favorite,
            myRating = states.rating.rating,
            watchlist = states.watchlist
        )
    }

    override suspend fun getTVView(id: Long, sessionId: String): TvView {
        val details = getTVDetails(id)
        val images = getTvsImages(id)
        val credits = getTvsCredits(id)
        val states = getTvAccountStates(id, sessionId)
        return TvView(
            createdBy = details.createdBy,
            country = details.country,
            genres = details.genres,
            episodes = details.episodes,
            homepage = details.homepage,
            id = details.id,
            originalLanguage = details.originalLanguage,
            overview = details.overview,
            popularity = details.popularity,
            posterPath = details.posterPath,
            companies = details.companies,
            countries = details.countries,
            runtime = details.runtime,
            languages = details.languages,
            firstAirDate = details.firstAirDate,
            inProduction = details.inProduction,
            lastAirDate = details.lastAirDate,
            lastEpisode = details.lastEpisode,
            networks = details.networks,
            numOfSeasons = details.numOfSeasons,
            originalName = details.originalName,
            seasons = details.seasons,
            spokenLanguages = details.spokenLanguages,
            type = details.type,
            status = details.status,
            tagline = details.tagline,
            name = details.name,
            rating = details.rating,
            average = details.average,
            backdrops = images.backdrops,
            posters = images.posters,
            cast = credits.cast,
            crew = credits.crew,
            videos = getTvVideos(id),
            recommendations = getRecommendationTvs(id),
            similar = getSimilarTvs(id),
            favorite = states.favorite,
            myRating = states.rating.rating,
            watchlist = states.watchlist
        )
    }

    override suspend fun getPersonView(id: Long): PersonView {
        val details = getPersonDetails(id)
        val profiles = getPersonsImages(id)
        return PersonView(
            biography = details.biography,
            knownFor = details.knownFor,
            deathday = details.deathday,
            id = details.id,
            name = details.name,
            alsoKnowsAs = details.alsoKnowsAs,
            birthday = details.birthday,
            gender = details.gender,
            popularity = details.popularity,
            placeOfBirth = details.placeOfBirth,
            profilePath = details.profilePath,
            adult = details.adult,
            imdbId = details.imdbId,
            homepage = details.homepage,
            profilesPhoto = profiles
        )
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

    private suspend fun getMovieDetails(id: Long) : FilmDetails {
        val response = service.getMovieDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: FilmDetails()
        } else {
            FilmDetails()
        }
    }

    private suspend fun getMoviesImages(id: Long): ImageData {
        val response = service.getMovieImages(id = id)
        return if(response.isSuccessful) {
            response.body() ?: ImageData()
        } else {
            ImageData()
        }
    }

    private suspend fun getMoviesCredits(id: Long): CreditsResponse {
        val response = service.getMovieCredits(id = id)
        return if(response.isSuccessful) {
            response.body() ?: CreditsResponse()
        } else {
            CreditsResponse()
        }
    }

    private suspend fun getMovieVideos(id: Long): List<TrailerResult> {
        val response = service.getMovieVideos(id = id)
        return if(response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    private suspend fun getRecommendationMovies(id: Long): List<Film> {
        val response = service.getRecommendationMovies(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    private suspend fun getSimilarMovies(id: Long): List<Film> {
        val response = service.getSimilarMovies(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    private suspend fun getMovieAccountStates(id: Long, sessionId: String): AccountStates {
        val response = service.getMovieAccountStates(id = id, sessionId = sessionId)
        val gson = GsonBuilder()
            .registerTypeAdapter(
                AccountStates::class.java,
                AccountStates.AccountStatesDeserializer()
            )
            .create()
        return gson.fromJson(response, AccountStates::class.java)
    }

    private suspend fun getTVDetails(id: Long): TvDetails {
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

    private suspend fun getTvsImages(id: Long): ImageData {
        val response = service.getTvImages(id = id)
        return if(response.isSuccessful) {
            response.body() ?: ImageData()
        } else {
            ImageData()
        }
    }

    private suspend fun getTvsCredits(id: Long): CreditsResponse {
        val response = service.getTvCredits(id = id)
        return if(response.isSuccessful) {
            response.body() ?: CreditsResponse()
        } else {
            CreditsResponse()
        }
    }

    private suspend fun getTvVideos(id: Long): List<TrailerResult> {
        val response = service.getTvVideos(id = id)
        return if(response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    private suspend fun getRecommendationTvs(id: Long): List<TV> {
        val response = service.getRecommendationTvs(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    private suspend fun getSimilarTvs(id: Long): List<TV> {
        val response = service.getSimilarTvs(id = id)
        return if(response.isSuccessful) {
            response.body()?.movies ?: emptyList()
        } else { emptyList() }
    }

    private suspend fun getTvAccountStates(id: Long, sessionId: String): AccountStates {
        val response = service.getTvAccountStates(id = id, sessionId = sessionId)
        val gson = GsonBuilder()
            .registerTypeAdapter(
                AccountStates::class.java,
                AccountStates.AccountStatesDeserializer()
            )
            .create()
        return gson.fromJson(response, AccountStates::class.java)
    }

    private suspend fun getPersonDetails(id: Long): PersonDetails {
        val response = service.getPersonDetails(id = id, language = "ru-RU")
        return if(response.isSuccessful) {
            response.body() ?: PersonDetails()
        } else {
            PersonDetails()
        }
    }

    private suspend fun getPersonsImages(id: Long): List<ImageUrlPath> {
        val response = service.getPersonImages(id = id)
        return if(response.isSuccessful) {
            response.body()?.profiles ?: emptyList()
        } else {
            emptyList()
        }
    }
}