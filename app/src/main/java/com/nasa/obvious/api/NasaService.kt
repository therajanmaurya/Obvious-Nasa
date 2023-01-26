package com.nasa.obvious.api

import com.nasa.obvious.models.Nasa
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface NasaService {
    @GET("obvious/take-home-exercise-data/trunk/nasa-pictures.json")
    fun listNasaImage(): Single<List<Nasa>>
}