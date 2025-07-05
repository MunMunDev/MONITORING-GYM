package com.abcd.monitoring_gym.data.database.repository

import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.data.model.PelatihanModel
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
}