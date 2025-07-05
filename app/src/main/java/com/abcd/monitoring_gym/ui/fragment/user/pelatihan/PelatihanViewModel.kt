package com.abcd.monitoring_gym.ui.fragment.user.pelatihan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.repository.PelatihanRepository
import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PelatihanViewModel @Inject constructor(
    private val repositoryPelatihan: PelatihanRepository
) : ViewModel() {
    private val _jenisPelatihan = MutableLiveData<UIState<ArrayList<JenisPelatihanModel>>>()
    val getJenisPelatihan: LiveData<UIState<ArrayList<JenisPelatihanModel>>> = _jenisPelatihan

    private val _pelatihan = MutableLiveData<UIState<ArrayList<PelatihanModel>>>()
    val getPelatihan : LiveData<UIState<ArrayList<PelatihanModel>>> = _pelatihan

    private fun fetchJenisPelatihan(){
        viewModelScope.launch {
            try {
                _jenisPelatihan.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryPelatihan.getJenisPelatihan()
                _jenisPelatihan.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _jenisPelatihan.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    private fun fetchPelatihan(){
        viewModelScope.launch {
            try {
                _pelatihan.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryPelatihan.getPelatihan()
                _pelatihan.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _pelatihan.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

    fun loadData(){
        fetchJenisPelatihan()
        fetchPelatihan()
    }
}