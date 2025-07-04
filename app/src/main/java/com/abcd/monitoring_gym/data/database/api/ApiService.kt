package com.abcd.monitoring_gym.data.database.api

import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.data.model.ProgressModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.data.model.UserModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("monitoring-gym/api/get.php")
    suspend fun getUser(
        @Query("get_user") get_user: String,
        @Query("username") username: String,
        @Query("password") password: String,
    ): UserModel

    @GET("monitoring-gym/api/get.php")
    suspend fun getPesanan(
        @Query("get_pesanan") get_pesanan: String,
        @Query("id_user") id_user: Int,
    ): ArrayList<PesananModel>
    // POST

    // User
    @FormUrlEncoded
    @POST("monitoring-gym/api/post.php")
    suspend fun postRegister(
        @Field("add_user") addUser:String,
        @Field("nama") nama:String,
        @Field("nomor_hp") nomorHp:String,
        @Field("alamat") alamat:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("sebagai") sebagai:String
    ): ResponseModel

}