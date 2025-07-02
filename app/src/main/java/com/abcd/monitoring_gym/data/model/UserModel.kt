package com.abcd.monitoring_gym.data.model

import com.google.gson.annotations.SerializedName

class UserModel (
    @SerializedName("id_user")
    var idUser: String? = null,

    @SerializedName("nama")
    var nama: String? = null,

    @SerializedName("nomor_hp")
    var nomor_hp: String? = null,

    @SerializedName("alamat")
    var alamat: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("sebagai")
    var sebagai: String? = null,
)