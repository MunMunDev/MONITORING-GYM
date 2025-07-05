package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
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

    @SerializedName("gambar")
    var gambar: String? = null,

    @SerializedName("jenis_pelatiihan")
    var jenis_pelatiihan: JenisPelatihanModel? = null,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(JenisPelatihanModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_pelatihan)
        parcel.writeString(id_jenis_pelatihan)
        parcel.writeString(pelatihan)
        parcel.writeString(deskripsi)
        parcel.writeString(hari_khusus)
        parcel.writeString(harga)
        parcel.writeString(gambar)
        parcel.writeParcelable(jenis_pelatiihan, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PelatihanModel> {
        override fun createFromParcel(parcel: Parcel): PelatihanModel {
            return PelatihanModel(parcel)
        }

        override fun newArray(size: Int): Array<PelatihanModel?> {
            return arrayOfNulls(size)
        }
    }
}