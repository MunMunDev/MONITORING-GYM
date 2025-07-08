package com.abcd.monitoring_gym.ui.activity.user.pelatihan.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.data.database.repository.PelatihanRepository
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.data.model.ProgramModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.data.model.UserModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPelatihanViewModel @Inject constructor(
    private val repositoryPelatihan: PelatihanRepository
): ViewModel() {
    private var _pelatih = MutableLiveData<UIState<ArrayList<ProgramModel>>>()
    fun getPelatih(): LiveData<UIState<ArrayList<ProgramModel>>> = _pelatih

    private var _responseDaftarPelatihan = MutableLiveData<UIState<ResponseModel>>()
    fun getDaftarPelatihan(): LiveData<UIState<ResponseModel>> = _responseDaftarPelatihan

    fun fetchPelatih(idPelatihan: Int){
        viewModelScope.launch(Dispatchers.IO) {
            _pelatih.postValue(UIState.Loading)
            delay(1_000)
            try {
                val pelatihanTerdaftar = repositoryPelatihan.getPelatih(idPelatihan)
                _pelatih.postValue(UIState.Success(pelatihanTerdaftar))
            } catch (ex: Exception){
                _pelatih.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }

    fun postDaftarPelatihan(
        idUser: Int,
        idPelatih: Int,
        idDaftarPelatihan: Int,
        pelatihan: String,
        jenisPelatihan: String,
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _responseDaftarPelatihan.postValue(UIState.Loading)
            delay(1_000)
            try {
                val pelatihanTerdaftar = repositoryPelatihan.postDaftarPelatihan(
                    idUser, idPelatih, idDaftarPelatihan, pelatihan, jenisPelatihan
                )
                _responseDaftarPelatihan.postValue(UIState.Success(pelatihanTerdaftar))
            } catch (ex: Exception){
                _responseDaftarPelatihan.postValue(UIState.Failure("Gagal : ${ex.message}"))
            }
        }
    }


}