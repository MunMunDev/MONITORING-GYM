package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
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

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(PelatihanModel::class.java.classLoader),
        TODO("pelatih")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_intruksi)
        parcel.writeString(id_pelatihan)
        parcel.writeString(id_pelatih)
        parcel.writeString(intruksi)
        parcel.writeString(link_youtube)
        parcel.writeParcelable(pelatihan, flags)
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