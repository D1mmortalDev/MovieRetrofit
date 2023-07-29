package com.example.movieretrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDataInterface {
    @GET("/")
    fun getMovieDetails(@Query("apikey")apiKey:String, @Query("t")title:String): Call<MovieData>
}