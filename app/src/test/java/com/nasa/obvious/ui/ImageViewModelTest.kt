package com.nasa.obvious.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.nasa.obvious.api.NasaService
import com.nasa.obvious.data.Repository
import com.nasa.obvious.models.Nasa
import com.nasa.obvious.util.FakeNasaSource
import com.nasa.obvious.util.RxTrampolineSchedulerRule
import com.nasa.obvious.util.argumentCaptor
import com.nasa.obvious.util.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ImageViewModelTest {

    @get:Rule
    var rule = RxTrampolineSchedulerRule()

    @Rule
    @JvmField
    val liveDataRule = InstantTaskExecutorRule()

    private lateinit var imageViewModel: ImageViewModel

    private val nasaCaptor = argumentCaptor<List<Nasa>>()
    private lateinit var repository: Repository

    private val nasaService = mock<NasaService>()

    @Before
    fun setUp() {
        repository = Repository(nasaService)
        imageViewModel = ImageViewModel(repository, SavedStateHandle())
    }

    @Test
    fun fetchNasaListImage_whenListIsNotEmpty() {
        `when`(repository.fetchNasaListImage())
            .thenReturn(Single.just(arrayListOf(FakeNasaSource.createNasa())))

        imageViewModel.fetchNasaListImage()

        assert(imageViewModel.images.value!!.first!!.isNotEmpty())
        assert(imageViewModel.images.value!!.second == null)
    }

    @Test
    fun fetchNasaListImage_whenListIsEmpty() {
        `when`(repository.fetchNasaListImage()).thenReturn(Single.just(emptyList()))

        imageViewModel.fetchNasaListImage()

        assert(imageViewModel.images.value!!.first!!.isEmpty())
        assert(imageViewModel.images.value!!.second == null)
    }

    @Test
    fun fetchNasaListImage_whenExceptionOccur() {
        `when`(repository.fetchNasaListImage()).thenReturn(Single.error(Throwable()))

        imageViewModel.fetchNasaListImage()

        assert(imageViewModel.images.value!!.first.isNullOrEmpty())
        assert(imageViewModel.images.value!!.second != null)
    }
}