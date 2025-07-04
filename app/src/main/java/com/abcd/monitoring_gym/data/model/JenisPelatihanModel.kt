package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class JenisPelatihanModel (
    @SerializedName("id_jenis_pelatihan")
    var id_jenis_pelatihan: Int? = null,

    @SerializedName("jenis_pelatihan")
    var jenis_pelatihan: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_jenis_pelatihan)
        parcel.writeString(jenis_pelatihan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JenisPelatihanModel> {
        override fun createFromParcel(parcel: Parcel): JenisPelatihanModel {
            return JenisPelatihanModel(parcel)
        }

        override fun newArray(size: Int): Array<JenisPelatihanModel?> {
            return arrayOfNulls(size)
        }
    }
}