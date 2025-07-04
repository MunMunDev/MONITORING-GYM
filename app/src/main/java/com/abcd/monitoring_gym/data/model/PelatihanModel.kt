package com.abcd.monitoring_gym.data.model

import com.google.gson.annotations.SerializedName

class PelatihanModel (
    @SerializedName("id_pelatihan")
    var id_pelatihan: Int? = null,

    @SerializedName("id_jenis_pelatihan")
    var id_jenis_pelatihan: String? = null,

    @SerializedName("pelatihan")
    var pelatihan: String? = null,

    @SerializedName("deskripsi")
    var deskripsi: String? = null,

    @SerializedName("hari_khusus")
    var hari_khusus: String? = null,

    @SerializedName("harga")
    var harga: String? = null,

    @SerializedName("jenis_pelatiihan")
    var jenis_pelatiihan: JenisPelatihanModel? = null,

)