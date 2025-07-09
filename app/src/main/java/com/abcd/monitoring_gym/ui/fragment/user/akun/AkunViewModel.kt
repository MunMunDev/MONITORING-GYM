package com.abcd.monitoring_gym.ui.fragment.user.akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.repository.AgendaRepository
import com.abcd.monitoring_gym.data.database.repository.AkunRepository
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AkunViewModel @Inject constructor(
    private val repositoryAkun: AkunRepository,
    private val repositoryAgenda: AgendaRepository,
) : ViewModel() {
    private val _updateProgress = MutableLiveData<UIState<ResponseModel>>()
    val getUpdateProgress: LiveData<UIState<ResponseModel>> = _updateProgress

    private val _riwayat = MutableLiveData<UIState<ArrayList<PesananModel>>>()
    val getRiwayat: LiveData<UIState<ArrayList<PesananModel>>> = _riwayat

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

    fun fetchRiwayatPelatihan(idUser: Int){
        viewModelScope.launch {
            try {
                _riwayat.postValue(UIState.Loading)
                delay(1_000)
                val data = repositoryAgenda.getRiwayatPesanan(idUser)
                _riwayat.postValue(UIState.Success(data))
            } catch (ex: Exception) {
                _riwayat.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }
}