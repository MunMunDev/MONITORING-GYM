package com.abcd.monitoring_gym.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private var api: ApiService
): ViewModel(){
    private var _postRegister = MutableLiveData<UIState<ResponseModel>>()
    fun getRegister(): LiveData<UIState<ResponseModel>> = _postRegister

    fun postRegister(
        nama: String, nomorHp: String, alamat: String, username: String, password: String,
    ) {
        viewModelScope.launch {
            _postRegister.postValue(UIState.Loading)
            delay(1_000)
            try {
                val postRegister = api.postRegister(
                    "", nama, nomorHp, alamat, username, password, "user"
                )
                _postRegister.postValue(UIState.Success(postRegister))
            } catch (ex: Exception) {
                _postRegister.postValue(UIState.Failure("Error: ${ex.message}"))
            }
        }
    }

}