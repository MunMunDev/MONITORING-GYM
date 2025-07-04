package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ProgressModel (
    @SerializedName("id_progress")
    var id_progress: Int? = null,

    @SerializedName("id_pesanan")
    var id_pesanan: String? = null,

    @SerializedName("id_pelatih")
    var id_pelatih: String? = null,

    @SerializedName("intruksi")
    var intruksi: String? = null,

    @SerializedName("link_youtube")
    var link_youtube: String? = null,

    @SerializedName("sudah_tercapai")
    var sudah_tercapai: Int? = null,

    @SerializedName("pelatihan")
    var pelatihan: PelatihanModel? = null,

    @SerializedName("pelatih")
    var pelatih: UserModel? = null,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readParcelable(PelatihanModel::class.java.classLoader),
        parcel.readParcelable(UserModel::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_progress)
        parcel.writeString(id_pesanan)
        parcel.writeString(id_pelatih)
        parcel.writeString(intruksi)
        parcel.writeString(link_youtube)
        parcel.writeValue(sudah_tercapai)
        parcel.writeParcelable(pelatihan, flags)
        parcel.writeParcelable(pelatih, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProgressModel> {
        override fun createFromParcel(parcel: Parcel): ProgressModel {
            return ProgressModel(parcel)
        }

        override fun newArray(size: Int): Array<ProgressModel?> {
            return arrayOfNulls(size)
        }
    }
}