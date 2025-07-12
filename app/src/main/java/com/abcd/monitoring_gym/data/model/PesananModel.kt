package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Suppress("UNREACHABLE_CODE")
class PesananModel (
    @SerializedName("id_pesanan")
    var id_pesanan: Int? = null,

    @SerializedName("id_user")
    var id_user: String? = null,

    @SerializedName("id_pelatihan")
    var id_pelatihan: String? = null,

    @SerializedName("tPelatihan")
    var tPelatihan: String? = null,

    @SerializedName("deskripsi")
    var deskripsi: String? = null,

    @SerializedName("hari_khusus")
    var hari_khusus: String? = null,

    @SerializedName("jenis_pelatihan")
    var jenis_pelatihan: String? = null,

    @SerializedName("selesai")
    var selesai: String? = null,

    @SerializedName("tanggal")
    var tanggal: String? = null,

    @SerializedName("waktu")
    var waktu: String? = null,

    @SerializedName("user")
    var user: UserModel? = null,

    @SerializedName("pelatihan")
    var pelatihan: PelatihanModel? = null,

    @SerializedName("progress")
    var progress: ArrayList<ProgressModel>? = arrayListOf(),

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(UserModel::class.java.classLoader),
        parcel.readParcelable(PelatihanModel::class.java.classLoader),
        TODO("progress")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_pesanan)
        parcel.writeString(id_user)
        parcel.writeString(id_pelatihan)
        parcel.writeString(tPelatihan)
        parcel.writeString(deskripsi)
        parcel.writeString(hari_khusus)
        parcel.writeString(jenis_pelatihan)
        parcel.writeString(selesai)
        parcel.writeString(tanggal)
        parcel.writeString(waktu)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(pelatihan, flags)
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