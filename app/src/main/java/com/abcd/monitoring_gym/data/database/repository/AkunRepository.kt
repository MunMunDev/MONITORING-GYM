package com.abcd.monitoring_gym.data.database.repository

import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.data.model.ProgramModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.data.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AkunRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun postDaftarPelatihan(
        idUser: Int,
        nama: String,
        nomorHp: String,
        alamat: String,
        jenisKelamin: String,
        username: String,
        password: String,
        usernameLama: String,
    ):ResponseModel{
        return api.postUpdateAkun(
            "", idUser, nama, nomorHp, alamat, jenisKelamin, username, password, usernameLama
        )
    }

}