package com.abcd.monitoring_gym.utils.hilt

import com.abcd.aplikasipenjualanplafon.utils.TanggalDanWaktu
import com.abcd.monitoring_gym.data.database.api.ApiService
import com.abcd.monitoring_gym.ui.Constant
import com.abcd.monitoring_gym.utils.KonversiRupiah
import com.abcd.monitoring_gym.utils.LoadingAlertDialog
import com.abcd.monitoring_gym.utils.network.CheckNetwork
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun apiConfig(): ApiService = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .client(
            OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(ApiService::class.java)

    @Provides
    fun tanggalDanWaktu(): TanggalDanWaktu = TanggalDanWaktu()

    @Provides
    fun loading(): LoadingAlertDialog = LoadingAlertDialog()


    @Provides
    @Singleton
    fun checkNetwork(): CheckNetwork = CheckNetwork()

    @Provides
    @Singleton
    fun checkrupiah(): KonversiRupiah = KonversiRupiah()

}