package com.example.iss.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iss.model.AstronautsResponse
import com.example.iss.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Provides astronauts data
 */
@HiltViewModel
class AstronautsViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
) : ViewModel() {

    private val _astronautsLiveData = MutableLiveData<AstronautsResponse>()
    val astronautsLiveData: LiveData<AstronautsResponse>
        get() = _astronautsLiveData

    fun getAstronauts() {
        CoroutineScope(Dispatchers.IO).launch {
            val issAstronauts = baseRepository.getAstronauts()
            _astronautsLiveData.postValue(issAstronauts)
        }
    }
}