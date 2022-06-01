package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.models.pojo.*
import com.example.myapplication.models.pojo.view.TvView
import com.example.myapplication.repository.repositories.itemsinfos.TvInfoRepository
import com.example.myapplication.repository.services.TmdbService
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class TvInfoRepositoryImpl(private val service: TmdbService) : TvInfoRepository {
    override suspend fun execute(id: Long, sessionId: String): TvInfoRepository.Result {
        return try {
            val details = getTVDetails(id)
            val images = getTvsImages(id)
            val credits = getTvsCredits(id)
            val states = getTvAccountStates(id, sessionId)
            TvInfoRepository.Result.Success(
                TvView(
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
            )
        } catch (e: HttpException) {
            TvInfoRepository.Result.Error
        } catch (e: ConnectException) {
            TvInfoRepository.Result.Error
        } catch (e: UnknownHostException) {
            TvInfoRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            TvInfoRepository.Result.Error
        }
    }

    private suspend fun getTVDetails(id: Long): TvDetails {
        val response = service.getTvDetails(id = id, language = "ru-RU")
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                val seasons: MutableList<Season> = arrayListOf()
                responseBody.seasons.forEach {
                    it.showId = responseBody.id
                    seasons.add(it)
                }
                responseBody.seasons = seasons
                responseBody.lastEpisode.showId = responseBody.id
                responseBody
            } else {
                throw HttpException(response)
            }
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getTvsImages(id: Long): ImageData {
        val response = service.getTvImages(id = id)
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getTvsCredits(id: Long): CreditsResponse {
        val response = service.getTvCredits(id = id)
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getTvVideos(id: Long): List<TrailerResult> {
        val response = service.getTvVideos(id = id)
        return if (response.isSuccessful) {
            response.body()?.results ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getRecommendationTvs(id: Long): List<TV> {
        val response = service.getRecommendationTvs(id = id)
        return if (response.isSuccessful) {
            response.body()?.items ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getSimilarTvs(id: Long): List<TV> {
        val response = service.getSimilarTvs(id = id)
        return if (response.isSuccessful) {
            response.body()?.items ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
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
}