package com.abcd.monitoring_gym.data.database.api

import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.data.model.UserModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("e-kelontong/api/get.php")
    suspend fun getUser(
        @Query("get_user") get_user: String,
        @Query("username") username: String,
        @Query("password") password: String,
    ): ArrayList<UserModel>
    // POST

    // User
    @FormUrlEncoded
    @POST("e-kelontong/api/post.php")
    suspend fun addUser(
        @Field("add_user") addUser:String,
        @Field("nama") nama:String,
        @Field("nomor_hp") nomorHp:String,
        @Field("id_kecamatan") idKecamatan:String,
        @Field("detail_alamat") detailAlamat:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("sebagai") sebagai:String
    ): ResponseModel

}