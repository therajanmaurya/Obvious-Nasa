package com.nasa.obvious.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nasa.obvious.data.Repository
import com.nasa.obvious.models.Nasa
import com.nasa.obvious.testing.OpenForTesting
import com.nasa.obvious.utils.subscribeSingleOnMain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@OpenForTesting
class ImageViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val images = MutableLiveData<Pair<List<Nasa>?, Throwable?>?>().apply { value = null }
    val showProgress = MutableLiveData<Boolean>().apply { value = false }
    var currentPosition: Int = 0

    fun fetchNasaListImage() {
        showProgress.postValue(true)
        repository.fetchNasaListImage()
            .subscribeSingleOnMain { nasaList, throwable ->
                showProgress.postValue(false)
                images.postValue(Pair(nasaList, throwable))
            }
    }
}