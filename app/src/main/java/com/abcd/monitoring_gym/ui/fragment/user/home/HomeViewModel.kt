package com.abcd.monitoring_gym.ui.fragment.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.repository.AgendaRepository
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryAgenda: AgendaRepository,
): ViewModel() {

    private val _progress = MutableLiveData<UIState<ArrayList<PesananModel>>>()
    val getPesanan : LiveData<UIState<ArrayList<PesananModel>>> = _progress

    fun fetchPesanan(idUser: Int){
        viewModelScope.launch {
            try {
                _progress.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryAgenda.getPesanan(idUser)
                _progress.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _progress.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

}