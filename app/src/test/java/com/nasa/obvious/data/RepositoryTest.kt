package com.nasa.obvious.data

import com.nasa.obvious.api.NasaService
import com.nasa.obvious.util.FakeNasaSource
import com.nasa.obvious.util.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`

@RunWith(JUnit4::class)
class RepositoryTest {

    private lateinit var repository: Repository

    private val nasaService = mock<NasaService>()

    @Before
    fun setUp() {
        repository = Repository(nasaService)
    }

    @Test
    fun fetchNasaList_whenListNotEmpty() {
        `when`(repository.fetchNasaListImage())
            .thenReturn(Single.just(arrayListOf(FakeNasaSource.createNasa())))

        repository.fetchNasaListImage()
            .test()
            .assertValue(arrayListOf(FakeNasaSource.createNasa()))
    }

    @Test
    fun fetchNasaListImage_whenListEmpty() {
        `when`(repository.fetchNasaListImage()).thenReturn(Single.just(emptyList()))

        repository.fetchNasaListImage()
            .test()
            .assertValue(emptyList())
    }

    @Test
    fun fetchNasaListImage_whenThrowException() {
        `when`(repository.fetchNasaListImage())
            .thenReturn(Single.error(Throwable()))

        repository.fetchNasaListImage()
            .test()
            .assertFailure(Throwable::class.java)
    }
}