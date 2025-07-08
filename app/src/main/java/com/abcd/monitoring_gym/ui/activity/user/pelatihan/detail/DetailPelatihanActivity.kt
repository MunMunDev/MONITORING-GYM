package com.abcd.monitoring_gym.ui.activity.user.pelatihan.detail

import android.annotation.SuppressLint
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
import com.abcd.monitoring_gym.databinding.ActivityDetailPelatihanBinding
import com.abcd.monitoring_gym.utils.Constant
import com.abcd.monitoring_gym.utils.KonversiRupiah
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
    private var idPelatihTerpilih = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPelatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchDataPreviously()
        setSharedPreferences()
        setButton()
        setTopAppBar()
        getPelatih()
    }

    private fun fetchDataPreviously() {
        val extras: Bundle? = intent.extras
        if(extras != null){
            dataPelatihan = extras.getParcelable("pelatihan")!!

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
                idPelatihTerpilih = program.id_pelatih!!
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

            }
        }
    }

    private fun setData(data: PelatihanModel){
        binding.apply {
            val hariKhusus = if(data.hari_khusus != null ) data.hari_khusus else "-"
            tvJenisPelatihan.text = data.jenis_pelatihan?.jenis_pelatihan
            tvNamaPelatihan.text = data.pelatihan
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

    @SuppressLint("SetTextI18n")
    private fun showDialogDaftarPelatihan(){
//        val view = AlertDialogDaftarBinding.inflate(layoutInflater)
//        val alertDialog = AlertDialog.Builder(this@DetailPelatihanActivity)
//        alertDialog.apply {
//            setView(view.root)
//            setCancelable(false)
//        }
//        val dialogInputan = alertDialog.create()
//        dialogInputan.show()
//
//        view.apply {
//            // set information
//            tvTitleKonfirmasi.text = namaPelatihan
//            tvBodyKonfirmasi.text = """
//                Apakah anda ingin daftar $namaPelatihan ?
//                Pelatihan ini Gratis.
//                Klik konfirmasi untuk mendaftar
//            """.trimIndent()
//
//            // Button
//            btnKonfirmasi.setOnClickListener {
//                postDaftarGratis()
//                dialogInputan.dismiss()
//            }
//            btnBatal.setOnClickListener {
//                dialogInputan.dismiss()
//            }
//        }
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