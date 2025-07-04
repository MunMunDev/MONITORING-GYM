package com.abcd.monitoring_gym.data.model

import com.google.gson.annotations.SerializedName

class PesananModel (
    @SerializedName("id_pesanan")
    var id_pesanan: Int? = null,

    @SerializedName("id_pelatihan")
    var id_pelatihan: String? = null,

    @SerializedName("pelatihan")
    var pelatihan: String? = null,

    @SerializedName("jenis_pelatihan")
    var jenis_pelatihan: String? = null,

    @SerializedName("selesai")
    var selesai: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("pelatihan_model")
    var pelatihan_model: PelatihanModel? = null,

)