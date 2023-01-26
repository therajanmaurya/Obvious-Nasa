package com.nasa.obvious.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nasa.obvious.data.Repository
import com.nasa.obvious.models.Nasa
import com.nasa.obvious.utils.subscribeSingleOnMain
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val images = MutableLiveData<List<Nasa>>().apply { value = emptyList() }

    fun fetchNasaListImage() {
        repository.fetchNasaListImage()
            .subscribeSingleOnMain { nasas, throwable ->
                images.postValue(nasas)
            }
    }
}