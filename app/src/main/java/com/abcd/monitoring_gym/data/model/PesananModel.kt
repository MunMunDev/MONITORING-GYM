package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
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

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(PelatihanModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_pesanan)
        parcel.writeString(id_pelatihan)
        parcel.writeString(pelatihan)
        parcel.writeString(jenis_pelatihan)
        parcel.writeString(selesai)
        parcel.writeString(tanggal)
        parcel.writeString(waktu)
        parcel.writeParcelable(pelatihan_model, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PesananModel> {
        override fun createFromParcel(parcel: Parcel): PesananModel {
            return PesananModel(parcel)
        }

        override fun newArray(size: Int): Array<PesananModel?> {
            return arrayOfNulls(size)
        }
    }
}