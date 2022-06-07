package com.example.myapplication.repository.impl.itemsonfos

import com.example.myapplication.models.pojo.*
import com.example.myapplication.models.pojo.view.PersonView
import com.example.myapplication.repository.repositories.itemsinfos.PersonInfoRepository
import com.example.myapplication.repository.services.TmdbService
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PersonInfoRepositoryImpl(private val service: TmdbService) : PersonInfoRepository {
    override suspend fun execute(id: Long): PersonInfoRepository.Result {
        return try {
            val details = getPersonDetails(id)
            val profiles = getPersonsImages(id)
            val tvCredits = getPersonTvCredits(id)
            val movieCredits = getPersonMovieCredits(id)
            val creditsList = mutableListOf<BaseItem>()
            creditsList.apply {
                addAll(tvCredits.crew)
                addAll(tvCredits.cast)
                addAll(movieCredits.cast)
                addAll(movieCredits.crew)
            }
            PersonInfoRepository.Result.Success(
                PersonView(
                    biography = details.biography,
                    deathday = details.deathday,
                    id = details.id,
                    name = details.name,
                    alsoKnowsAs = details.alsoKnowsAs,
                    birthday = details.birthday,
                    gender = details.gender,
                    placeOfBirth = details.placeOfBirth,
                    profilePath = details.profilePath,
                    profilesPhoto = profiles,
                    knownFor = creditsList
                )
            )
        } catch (e: HttpException) {
            PersonInfoRepository.Result.Error
        } catch (e: ConnectException) {
            PersonInfoRepository.Result.Error
        } catch (e: UnknownHostException) {
            PersonInfoRepository.Result.Error
        } catch (e: SocketTimeoutException) {
            PersonInfoRepository.Result.Error
        }
    }

    private suspend fun getPersonDetails(id: Long): PersonDetails {
        val response = service.getPersonDetails(id = id, language = "ru-RU")
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getPersonsImages(id: Long): List<ImageUrlPath> {
        val response = service.getPersonImages(id = id)
        return if (response.isSuccessful) {
            response.body()?.profiles ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getPersonMovieCredits(id: Long): PersonCredits<Film> {
        val response = service.getPersonMovieCredits(id = id)
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }

    private suspend fun getPersonTvCredits(id: Long): PersonCredits<TV> {
        val response = service.getPersonTvCredits(id = id)
        return if (response.isSuccessful) {
            response.body() ?: throw HttpException(response)
        } else {
            throw HttpException(response)
        }
    }
}