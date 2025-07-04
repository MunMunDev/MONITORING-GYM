package com.abcd.monitoring_gym.data.model

import com.google.gson.annotations.SerializedName

class ProgressModel (
    @SerializedName("id_intruksi")
    var id_intruksi: Int? = null,

    @SerializedName("id_pelatihan")
    var id_pelatihan: String? = null,

    @SerializedName("id_pelatih")
    var id_pelatih: String? = null,

    @SerializedName("intruksi")
    var intruksi: String? = null,

    @SerializedName("link_youtube")
    var link_youtube: String? = null,

    @SerializedName("pelatihan")
    var pelatihan: PelatihanModel? = null,

    @SerializedName("pelatih")
    var pelatih: UserModel? = null,

)