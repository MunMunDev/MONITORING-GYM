package com.abcd.monitoring_gym.data.database.repository

import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AgendaRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun getPesanan(
        idUser: Int
    ):ArrayList<PesananModel>{
        return api.getPesanan("", idUser)
    }

    suspend fun getRiwayatPesanan(
        idUser: Int
    ):ArrayList<PesananModel>{
        return api.getRiwayatPesanan("", idUser)
    }

    suspend fun postUpdateProgress(
        idProgress: Int
    ): ResponseModel {
        return api.postUpdateCheckAgenda("", idProgress)
    }

//    suspend fun getPelatihan(
//    ):ArrayList<PelatihanModel>{
//        return api.getPelatihan("")
//    }

}