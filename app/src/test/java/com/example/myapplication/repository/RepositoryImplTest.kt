package com.example.myapplication.repository

import com.example.myapplication.movies.Movie
import com.example.myapplication.movies.MoviesResponse
import com.example.myapplication.movies.TmdbService
import com.example.myapplication.repository.inMemory.RepositoryImpl
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.FileReader


class RepositoryImplTest {

    private lateinit var movieList: List<Movie>

    private val moviesResponseObj: MoviesResponse = Gson()
        .fromJson(
            FileReader("/home/user/AndroidStudioProjects/MyApplication/app/src/test/resources/success_response.json"),
            MoviesResponse::class.java
        )

    private val correctList = moviesResponseObj.movies

    @Before
    fun initServiceAndRepository(){
        val service: TmdbService = mock(TmdbService::class.java)

        //Что нужно писать в thenReturn, если метод возвращает Call<MoviesResponse>
      // `when`(service.getTopRatedMovies(language = "ru-RU")).thenReturn(correctList)

        val repositoryImpl = RepositoryImpl(service)
       // repositoryImpl.getData(::onSuccess, ::onError)
    }

    private fun onSuccess(list: List<Movie>){
        movieList = list
    }

    private fun onError(){

    }

    @Test
    fun responseIsCorrect(){
        assertEquals(correctList, movieList)
    }

}