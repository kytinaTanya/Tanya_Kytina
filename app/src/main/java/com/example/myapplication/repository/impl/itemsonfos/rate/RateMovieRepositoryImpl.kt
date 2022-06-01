package com.example.myapplication.repository.impl.itemsonfos.rate

import com.example.myapplication.models.marks.RatingValue
import com.example.myapplication.repository.repositories.itemsinfos.RateItemResult
import com.example.myapplication.repository.repositories.itemsinfos.RateMovieRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RateMovieRepositoryImpl(private val service: TmdbService) : RateMovieRepository {

    override suspend fun execute(id: Long, sessionId: String, rating: Float): RateItemResult {
        return try {
            val response = service.rateMovie(id = id,sessionId = sessionId, body = RatingValue(rating))
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