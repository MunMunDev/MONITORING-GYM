package com.abcd.monitoring_gym.data.database.api

import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.data.model.ProgramModel
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

    @GET("monitoring-gym/api/get.php")
    suspend fun getPelatihan(
        @Query("get_pelatihan") get_pelatihan: String,
    ): ArrayList<PelatihanModel>

    @GET("monitoring-gym/api/get.php")
    suspend fun getJenisPelatihan(
        @Query("get_jenis_pelatihan") get_jenis_pelatihan: String,
    ): ArrayList<JenisPelatihanModel>

    @GET("monitoring-gym/api/get.php")
    suspend fun getPelatih(
        @Query("get_pelatih") get_pelatih: String,
        @Query("id_pelatihan") id_pelatihan: Int,
    ): ArrayList<ProgramModel>


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

    //
    @FormUrlEncoded
    @POST("monitoring-gym/api/post.php")
    suspend fun postUpdateCheckAgenda(
        @Field("update_check_progress") update_check_progress:String,
        @Field("id_progress") id_progress:Int,
    ): ResponseModel

    // Daftar Pelatihan
    @FormUrlEncoded
    @POST("monitoring-gym/api/post.php")
    suspend fun postDaftarPelatihan(
        @Field("post_daftar_pelatihan") post_daftar_pelatihan:String,
        @Field("id_user") id_user:Int,
        @Field("id_pelatih") id_pelatih:Int,
        @Field("id_pelatihan") id_pelatihan:Int,
        @Field("pelatihan") pelatihan:String,
        @Field("jenis_pelatihan") jenis_pelatihan:String,
    ): ResponseModel

    //
    @FormUrlEncoded
    @POST("monitoring-gym/api/post.php")
    suspend fun postUpdateAkun(
        @Field("update_akun") update_akun:String,
        @Field("id_user") id_user:Int,
        @Field("nama") nama:String,
        @Field("nomor_hp") nomor_hp:String,
        @Field("alamat") alamat:String,
        @Field("jenis_kelamin") jenis_kelamin:String,
        @Field("username") username:String,
        @Field("password") password:String,
        @Field("username_lama") username_lama:String,
    ): ResponseModel

}