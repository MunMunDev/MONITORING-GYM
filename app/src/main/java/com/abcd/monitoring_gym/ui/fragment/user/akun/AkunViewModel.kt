package com.abcd.monitoring_gym.ui.fragment.user.akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.repository.AkunRepository
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(
    private val repositoryAkun: AkunRepository
) : ViewModel() {
    private val _updateProgress = MutableLiveData<UIState<ResponseModel>>()
    val getUpdateProgress: LiveData<UIState<ResponseModel>> = _updateProgress

    fun postUpdateProgress(
        idUser: Int,
        nama: String,
        nomorHp: String,
        alamat: String,
        jenisKelamin: String,
        username: String,
        password: String,
        usernameLama: String,
    ){
        viewModelScope.launch {
            try {
                _updateProgress.postValue(UIState.Loading)
                delay(5_00)
                val data = repositoryAkun.postDaftarPelatihan(
                    idUser, nama, nomorHp, alamat, jenisKelamin, username, password, usernameLama
                )
                _updateProgress.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _updateProgress.postValue(UIState.Failure("error: ${ex.message}"))
            }
        }
    }
}