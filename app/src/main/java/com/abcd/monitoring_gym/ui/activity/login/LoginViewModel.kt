package com.abcd.monitoring_gym.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.data.model.UserModel
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: ApiService
) : ViewModel(){
    private var _user = MutableLiveData<UIState<UserModel>>()
    fun getUser() : LiveData<UIState<UserModel>> = _user

    fun fetchUser(
        username: String, password: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(UIState.Loading)
            delay(1_000)
            try {
                val data = api.getUser("", username, password)
                _user.postValue(UIState.Success(data))
            } catch (ex: Exception){
                _user.postValue(UIState.Failure("Error : ${ex.message}"))
            }
        }
    }

}