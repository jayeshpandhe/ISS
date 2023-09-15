package com.example.iss.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.iss.db.ISSPosition
import com.example.iss.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Provides ISS location history data
 */
@HiltViewModel
class ISSLocationHistoryViewModel @Inject constructor(
    private val baseRepository: BaseRepository,
) : ViewModel() {

    private val _issLocationsHistoryLiveData = MutableLiveData<List<ISSPosition>>()
    val issLocationsHistoryLiveData: LiveData<List<ISSPosition>>
        get() = _issLocationsHistoryLiveData

    fun getISSLocationsHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val issPositions = baseRepository.getISSPositionsHistory()
            _issLocationsHistoryLiveData.postValue(issPositions)
        }
    }
}