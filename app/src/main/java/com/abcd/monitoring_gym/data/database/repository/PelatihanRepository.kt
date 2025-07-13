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
class PelatihanRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getPelatihan(
    ):ArrayList<PelatihanModel>{
        return api.getPelatihan("")
    }

    suspend fun getJenisPelatihan(
    ):ArrayList<JenisPelatihanModel>{
        return api.getJenisPelatihan("")
    }

    suspend fun getPelatih(
        idPelatihan: Int
    ):ArrayList<ProgramModel>{
        return api.getPelatih("", idPelatihan)
    }

    suspend fun postDaftarPelatihan(
        idUser: Int,
        idPelatih: Int,
        idPelatihan: Int,
        pelatihan: String,
        jenisPelatihan: String,
        deskripsi: String,
        hariKhusus: String,
        harga: String,
    ):ResponseModel{
        return api.postDaftarPelatihan(
            "", idUser, idPelatih, idPelatihan, pelatihan, jenisPelatihan, deskripsi, hariKhusus, harga
        )
    }

}