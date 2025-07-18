package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class IntruksiModel (
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

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        TODO("pelatih"),
        TODO("pelatihan")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id_program)
        parcel.writeString(id_pelatih)
        parcel.writeString(id_pelatihan)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IntruksiModel> {
        override fun createFromParcel(parcel: Parcel): IntruksiModel {
            return IntruksiModel(parcel)
        }

        override fun newArray(size: Int): Array<IntruksiModel?> {
            return arrayOfNulls(size)
        }
    }
}