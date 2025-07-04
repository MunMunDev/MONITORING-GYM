package com.abcd.monitoring_gym.ui.activity.user.agenda.detail_agenda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.repository.AgendaRepository
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAgendaViewModel @Inject constructor(
    private val repositoryAgenda: AgendaRepository
): ViewModel(){
    private val _updateProgress = MutableLiveData<UIState<ResponseModel>>()
    val getUpdateProgress: LiveData<UIState<ResponseModel>> = _updateProgress

    fun postUpdateProgress(
        idProgress: Int
    ){
        viewModelScope.launch {
            try {
                _updateProgress.postValue(UIState.Loading)
                delay(5_00)
                val data = repositoryAgenda.postUpdateProgress(idProgress)
                _updateProgress.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateProgress.postValue(UIState.Failure("error: ${ex.message}"))
            }
        }
    }
}