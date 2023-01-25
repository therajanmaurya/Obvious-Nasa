package com.nasa.obvious.api

import retrofit2.Call
import retrofit2.http.GET

interface NasaService {

    @GET("obvious/take-home-exercise-data/trunk/nasa-pictures.json")
    fun listNasaPicture(): Call<Any>
}