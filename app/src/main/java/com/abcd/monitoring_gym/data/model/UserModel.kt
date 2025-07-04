package com.abcd.monitoring_gym.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserModel (
    @SerializedName("id_user")
    var idUser: Int? = null,

    @SerializedName("nama")
    var nama: String? = null,

    @SerializedName("nomor_hp")
    var nomor_hp: String? = null,

    @SerializedName("alamat")
    var alamat: String? = null,

    @SerializedName("jenis_kelamin")
    var jenis_kelamin: String? = null,

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("sebagai")
    var sebagai: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(idUser)
        parcel.writeString(nama)
        parcel.writeString(nomor_hp)
        parcel.writeString(alamat)
        parcel.writeString(jenis_kelamin)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(sebagai)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}