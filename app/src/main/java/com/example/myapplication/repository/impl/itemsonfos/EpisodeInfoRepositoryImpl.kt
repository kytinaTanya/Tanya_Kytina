package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.models.marks.AccountStates
import com.example.myapplication.repository.repositories.itemsinfos.EpisodeInfoRepository
import com.example.myapplication.repository.services.TmdbService
import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EpisodeInfoRepositoryImpl(private val service: TmdbService) : EpisodeInfoRepository {
    override suspend fun execute(tvId: Long, seasonNum: Int, episodeNum: Int, sessionId: String): EpisodeInfoRepository.Result {
        return try {
            val response =
                service.getEpisodeDetails(tvId = tvId,
                    seasonNum = seasonNum,
                    episodeNum = episodeNum)
            val ratedState = getRatedState(tvId, seasonNum, episodeNum, sessionId)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    EpisodeInfoRepository.Result.Success(response.body()!!.apply { userRating = ratedState.rating })
                } else {
                    EpisodeInfoRepository.Result.Error
                }
            } else {
                EpisodeInfoRepository.Result.Error
            }
        } catch (e: HttpException) {
            EpisodeInfoRepository.Result.Error
        } catch (e: ConnectException) {
            EpisodeInfoRepository.Result.Error
        } catch (e: UnknownHostException) {
            EpisodeInfoRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            EpisodeInfoRepository.Result.Error
        }
    }

    private suspend fun getRatedState(tvId: Long, seasonId: Int, episodeId: Int, sessionId: String): AccountStates {
        val response = service.getEpisodeAccountStates(tvId = tvId, season = seasonId, episode = episodeId, sessionId = sessionId)
        val gson = GsonBuilder()
            .registerTypeAdapter(
                AccountStates::class.java,
                AccountStates.AccountStatesDeserializer()
            )
            .create()
        return gson.fromJson(response, AccountStates::class.java)
    }
}