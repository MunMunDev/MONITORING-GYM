package com.abcd.monitoring_gym.data.model

import com.google.gson.annotations.SerializedName

class ProgramModel (
    @SerializedName("id_program")
    var id_program: Int? = null,

    @SerializedName("id_pelatih")
    var id_pelatih: String? = null,

    @SerializedName("id_pelatihan")
    var id_pelatihan: String? = null,

    @SerializedName("pelatih")
    var pelatih: UserModel? = null,

    @SerializedName("pelatihan")
    var pelatihan: PelatihanModel? = null,

)