package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.pojo.*
import com.example.myapplication.models.pojo.view.MovieView
import com.example.myapplication.repository.repositories.itemsinfos.MovieInfoRepository
import com.example.myapplication.repository.services.TmdbService
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MovieInfoRepositoryImpl(val service: TmdbService) : MovieInfoRepository {
    override suspend fun execute(id: Long, sessionId: String): MovieInfoRepository.Result {
        return try {
            val details = getMovieDetails(id)
            val images = getMoviesImages(id)
            val credits = getMoviesCredits(id)
            val states = getMovieAccountStates(id, sessionId)
            MovieInfoRepository.Result.Success(
                MovieView(
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
                    favorite = states.favorite!!,
                    myRating = states.rating.rating,
                    watchlist = states.watchlist!!
                )
            )
        } catch (e: HttpException) {
            MovieInfoRepository.Result.Error
        } catch (e: ConnectException) {
            MovieInfoRepository.Result.Error
        } catch (e: UnknownHostException) {
            MovieInfoRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            MovieInfoRepository.Result.Error
        }
    }

    private suspend fun getMovieDetails(id: Long): FilmDetails {
        val response = service.getMovieDetails(id = id, language = "ru-RU")
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getMoviesImages(id: Long): ImageData {
        val response = service.getMovieImages(id = id)
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getMoviesCredits(id: Long): CreditsResponse {
        val response = service.getMovieCredits(id = id)
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getMovieVideos(id: Long): List<TrailerResult> {
        val response = service.getMovieVideos(id = id)
        return if (response.isSuccessful) {
            response.body()?.results ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getRecommendationMovies(id: Long): List<Film> {
        val response = service.getRecommendationMovies(id = id)
        return if (response.isSuccessful) {
            response.body()?.items ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getSimilarMovies(id: Long): List<Film> {
        val response = service.getSimilarMovies(id = id)
        return if (response.isSuccessful) {
            response.body()?.items ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
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
}