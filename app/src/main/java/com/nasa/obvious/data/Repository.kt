package com.nasa.obvious.data

import com.nasa.obvious.api.NasaService
import com.nasa.obvious.models.Nasa
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val nasaService: NasaService
) {

    fun fetchNasaListImage(): Single<List<Nasa>> {
        return nasaService.listNasaImage()
    }
}