package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.models.pojo.Episode
import com.example.myapplication.repository.repositories.itemsinfos.SeasonInfoRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SeasonInfoRepositoryImpl(private val service: TmdbService) : SeasonInfoRepository {
    override suspend fun execute(tvId: Long, seasonNum: Int): SeasonInfoRepository.Result {
        return try {
            val response = service.getSeasonDetails(tvId = tvId, seasonNum = seasonNum)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val episodes: MutableList<Episode> = arrayListOf()
                    responseBody.episodes.forEach {
                        it.showId = tvId
                        episodes.add(it)
                    }
                    responseBody.episodes = episodes
                    SeasonInfoRepository.Result.Success(responseBody)
                } else {
                    SeasonInfoRepository.Result.Error
                }
            } else {
                SeasonInfoRepository.Result.Error
            }
        } catch (e: HttpException) {
            SeasonInfoRepository.Result.Error
        } catch (e: ConnectException) {
            SeasonInfoRepository.Result.Error
        } catch (e: UnknownHostException) {
            SeasonInfoRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            SeasonInfoRepository.Result.Error
        }
    }
}