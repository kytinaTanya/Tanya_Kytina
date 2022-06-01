package com.example.myapplication.repository.impl.itemsonfos.rate

import com.example.myapplication.models.marks.RatingValue
import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult
import com.example.myapplication.repository.repositories.itemsinfos.rate.RateEpisodeRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RateEpisodeRepositoryImpl(private val service: TmdbService) : RateEpisodeRepository {
    override suspend fun execute(
        tvId: Long,
        season: Int,
        episode: Int,
        sessionId: String,
        rating: Float,
    ): RateItemResult {
        return try {
            val response = service.rateEpisode(tvId = tvId,
                season = season,
                episode = episode,
                sessionId = sessionId,
                body = RatingValue(rating))
            val result = response.body()
            if (result != null) {
                RateItemResult.Success(result)
            } else {
                RateItemResult.Error
            }
        } catch (e: HttpException) {
            RateItemResult.Error
        } catch (e: ConnectException) {
            RateItemResult.Error
        } catch (e: UnknownHostException) {
            RateItemResult.Error
        } catch (e: SocketTimeoutException) {
            RateItemResult.Error
        }
    }
}