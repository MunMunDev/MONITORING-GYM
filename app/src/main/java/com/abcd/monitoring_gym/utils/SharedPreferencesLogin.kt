package com.abcd.monitoring_gym.utils

import android.content.Context

class SharedPreferencesLogin(val context: Context) {
    val keyIdUser = "keyIdUser"
    val keyNama = "keyNama"
    val keyNomorHp = "keyNomorHp"
    val keyAlamat = "keyAlamat"
    val keyJenisKelamin = "keyJenisKelamin"
    val keyUsername = "keyUsername"
    val keyPassword = "keyPassword"
    val keySebagai = "keySebagai"

    var sharedPref = context.getSharedPreferences("sharedpreference_login", Context.MODE_PRIVATE)
    var editPref = sharedPref.edit()

    fun setLogin(id_user:Int, nama:String, nomorHp:String, alamat:String, jenisKelamin:String, username:String, password:String, sebagai:String){
        editPref.apply{
            putInt(keyIdUser, id_user)
            putString(keyNama, nama)
            putString(keyNomorHp, nomorHp)
            putString(keyAlamat, alamat)
            putString(keyJenisKelamin, jenisKelamin)
            putString(keyUsername, username)
            putString(keyPassword, password)
            putString(keySebagai, sebagai)
            apply()
        }
    }

    fun setLogout(){
        editPref.apply{
            putInt(keyIdUser, 0)
            putString(keyNama, "")
            putString(keyNomorHp, "")
            putString(keyAlamat, "")
            putString(keyJenisKelamin, "")
            putString(keyUsername, "")
            putString(keyPassword, "")
            putString(keySebagai, "")
            apply()
        }
    }

    fun getIdUser(): Int{
        return sharedPref.getInt(keyIdUser, 0)
    }
    fun getNama():String{
        return sharedPref.getString(keyNama, "").toString()
    }
    fun getNomorHp():String{
        return sharedPref.getString(keyNomorHp, "").toString()
    }
    fun getAlamat():String{
        return sharedPref.getString(keyAlamat, "").toString()
    }
    fun getJenisKelamin():String{
        return sharedPref.getString(keyJenisKelamin, "").toString()
    }
    fun getUsername():String{
        return sharedPref.getString(keyUsername, "").toString()
    }
    fun getPassword(): String{
        return sharedPref.getString(keyPassword, "").toString()
    }
    fun getSebagai(): String{
        return sharedPref.getString(keySebagai, "").toString()
    }
}