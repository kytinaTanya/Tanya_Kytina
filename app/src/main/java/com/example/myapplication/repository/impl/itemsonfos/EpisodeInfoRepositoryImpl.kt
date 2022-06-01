package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.repository.repositories.itemsinfos.EpisodeInfoRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class EpisodeInfoRepositoryImpl(private val service: TmdbService) : EpisodeInfoRepository {
    override suspend fun execute(tvId: Long, seasonNum: Int, episodeNum: Int): EpisodeInfoRepository.Result {
        return try {
            val response =
                service.getEpisodeDetails(tvId = tvId,
                    seasonNum = seasonNum,
                    episodeNum = episodeNum)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    EpisodeInfoRepository.Result.Success(response.body()!!)
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
}