package com.abcd.monitoring_gym.ui.activity.user.pelatihan.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.adapter.ProgramAdapter
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.data.model.ProgramModel
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.databinding.ActivityDetailPelatihanBinding
import com.abcd.monitoring_gym.utils.Constant
import com.abcd.monitoring_gym.utils.KonversiRupiah
import com.abcd.monitoring_gym.utils.LoadingAlertDialog
import com.abcd.monitoring_gym.utils.OnClickItem
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin
import com.abcd.monitoring_gym.utils.network.UIState
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
@Suppress("DEPRECATION")
class DetailPelatihanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPelatihanBinding
    private val viewModel: DetailPelatihanViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private var rupiah = KonversiRupiah()
    private var dataPelatihan : PelatihanModel? = null
    private lateinit var adapterPelatih: ProgramAdapter
    private var idPelatihTerpilih = 0
    private var idPelatihan = 0
    private var pelatihan = ""
    private var jenisPelatihan = ""

    private var loading = LoadingAlertDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPelatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchDataPreviously()
        setSharedPreferences()
        setButton()
        setTopAppBar()
        getPelatih()
        getDaftarPelatihan()
    }

    private fun fetchDataPreviously() {
        val extras: Bundle? = intent.extras
        if(extras != null){
            dataPelatihan = extras.getParcelable("pelatihan")!!
            idPelatihan = dataPelatihan?.id_pelatihan!!
            pelatihan = dataPelatihan?.pelatihan!!
            jenisPelatihan = dataPelatihan?.jenis_pelatihan?.jenis_pelatihan!!

            fetchPelatih(dataPelatihan?.id_pelatihan!!)
            setData(dataPelatihan!!)
        }
    }

    private fun fetchPelatih(idPelatihan: Int) {
        viewModel.fetchPelatih(idPelatihan)
    }

    private fun getPelatih(){
        viewModel.getPelatih().observe(this@DetailPelatihanActivity){result->
            when(result){
                is UIState.Loading-> setStartShimmerPelatih()
                is UIState.Failure-> setFailurePelatih(result.message)
                is UIState.Success-> setSuccessPelatih(result.data)
            }
        }
    }

    private fun setFailurePelatih(message: String) {
        Toast.makeText(this@DetailPelatihanActivity, message, Toast.LENGTH_SHORT).show()
        setStopShimmerPelatih()
    }

    private fun setSuccessPelatih(data: ArrayList<ProgramModel>) {
        if(data.isNotEmpty()){
            setAdapterPelatih(data)
        }
        setStopShimmerPelatih()
    }

    private fun setAdapterPelatih(data: ArrayList<ProgramModel>) {
        adapterPelatih = ProgramAdapter(data, object: OnClickItem.ClickProgram{
            override fun clickProgram(program: ProgramModel) {
                idPelatihTerpilih = program.id_pelatih?.trim()!!.toInt()
            }
        })
        binding.rvPelatih.apply {
            layoutManager = LinearLayoutManager(this@DetailPelatihanActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterPelatih
        }
    }

    private fun setSharedPreferences() {
        sharedPreferences = SharedPreferencesLogin(this@DetailPelatihanActivity)
    }

    private fun setTopAppBar() {
        binding.appNavbarDrawer.apply {
            tvTitle.text = dataPelatihan!!.pelatihan
            ivNavDrawer.visibility = View.GONE
            ivBack.visibility = View.VISIBLE
        }
    }

    private fun setButton(){
        binding.apply {
            appNavbarDrawer.ivBack.setOnClickListener {
                finish()
            }
            btnDaftar.setOnClickListener {
                postDaftarPelatihan(
                    sharedPreferences.getIdUser(), idPelatihTerpilih,
                    idPelatihan, pelatihan, jenisPelatihan
                )
            }
        }
    }

    private fun setData(data: PelatihanModel){
        binding.apply {
            val hariKhusus = if(data.hari_khusus != null ) data.hari_khusus else "-"
            tvNamaPelatihan.text = data.pelatihan
            tvJenisPelatihan.text = data.jenis_pelatihan?.jenis_pelatihan
            tvBiaya.text = rupiah.rupiah(data.harga!!.toLong())
            tvHariKhusus.text = hariKhusus
            tvDeskripsi.text = data.deskripsi

            Glide.with(this@DetailPelatihanActivity)
                .load("${Constant.LOCATION_GAMBAR}${data.gambar}")
                .placeholder(R.drawable.loading)
                .error(R.drawable.img_main)
                .into(ivGambarPelatihan)
        }
    }

    private fun postDaftarPelatihan(
        idUser: Int, idPelatih: Int, idPelatihan: Int,
        pelatihan:String, jenisPelatihan: String
    ){
        viewModel.postDaftarPelatihan(
            idUser, idPelatih, idPelatihan, pelatihan, jenisPelatihan
        )
    }

    private fun getDaftarPelatihan(){
        viewModel.getDaftarPelatihan().observe(this@DetailPelatihanActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@DetailPelatihanActivity)
                is UIState.Failure-> setFailureDaftarPelatih(result.message)
                is UIState.Success-> setSuccessDaftarPelatih(result.data)
            }
        }
    }

    private fun setFailureDaftarPelatih(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@DetailPelatihanActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun setSuccessDaftarPelatih(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status == "0"){
            Toast.makeText(this@DetailPelatihanActivity, "Berhasil Pesan Pelatihan", Toast.LENGTH_SHORT).show()
            finish()
        } else{
            Toast.makeText(this@DetailPelatihanActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setStartShimmerPelatih(){
        binding.apply {
            smPelatih.startShimmer()

            smPelatih.visibility = View.VISIBLE
            rvPelatih.visibility = View.GONE
        }
    }

    private fun setStopShimmerPelatih(){
        binding.apply {
            smPelatih.stopShimmer()

            smPelatih.visibility = View.GONE
            rvPelatih.visibility = View.VISIBLE
        }
    }
}