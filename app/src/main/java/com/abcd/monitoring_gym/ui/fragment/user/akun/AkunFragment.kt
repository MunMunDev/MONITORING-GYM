package com.abcd.monitoring_gym.ui.fragment.user.akun

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.data.model.UserModel
import com.abcd.monitoring_gym.databinding.AlertDialogAkunBinding
import com.abcd.monitoring_gym.databinding.FragmentAkunBinding
import com.abcd.monitoring_gym.ui.activity.login.LoginActivity
import com.abcd.monitoring_gym.utils.LoadingAlertDialog
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding
    private val viewModel: AkunViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private lateinit var userModel: UserModel
    private lateinit var tempUserModel: UserModel
    private var loading = LoadingAlertDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferencesLogin(requireContext())
        sharedPreferences.apply {
            userModel = UserModel(
                getIdUser(), getNama(), getNomorHp(), getAlamat(), getJenisKelamin(),
                getUsername(), getPassword(), getSebagai()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAkunBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavDrawer()
        setButton()
        setDataUser()
        getUpdateData()
    }

    private fun setNavDrawer() {
        binding.myAppBar.apply {
            ivNavDrawer.visibility = View.GONE
            tvTitle.visibility = View.GONE
        }
    }

    private fun setButton() {
        binding.apply {
            btnAkun.setOnClickListener {
                setAccountActive()
            }
            btnRiwayat.setOnClickListener {
                setHistoryActive()
            }
            btnUbahData.setOnClickListener {
                setShowEditProfile()
            }
            btnLogout.setOnClickListener {
                setLogout()
            }
        }
    }

    private fun setLogout() {
        sharedPreferences.setLogout()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }

    private fun setShowEditProfile() {
        val view = AlertDialogAkunBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(view.root)
            .setCancelable(false)
        val dialogInputan = alertDialog.create()
        dialogInputan.show()

        view.apply {
            etEditNama.setText(sharedPreferences.getNama())
            etEditNomorHp.setText(sharedPreferences.getNomorHp())
            etEditAlamat.setText(sharedPreferences.getAlamat())
            etEditUsername.setText(sharedPreferences.getUsername())
            etEditPassword.setText(sharedPreferences.getPassword())
            when(sharedPreferences.getJenisKelamin()){
                "Laki-laki"-> rbLakiLaki.isChecked = true
                "Perempuan"-> rbPerempuan.isChecked = true
            }

            var selectedGender = ""
            rgGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbLakiLaki -> selectedGender = "Laki-laki"
                    R.id.rbPerempuan -> selectedGender = "Perempuan"
                }
            }

            btnSimpan.setOnClickListener {
                var cek = false
                if(etEditNama.toString().isEmpty()){
                    etEditNama.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditAlamat.toString().isEmpty()){
                    etEditAlamat.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditNomorHp.toString().isEmpty()){
                    etEditNomorHp.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditUsername.toString().isEmpty()){
                    etEditUsername.error = "Tidak Boleh Kosong"
                    cek = true
                }
                if(etEditPassword.toString().isEmpty()){
                    etEditPassword.error = "Tidak Boleh Kosong"
                    cek = true
                }

                if(!cek){
                    val nama = etEditNama.text.toString()
                    val nomorHp = etEditNomorHp.text.toString()
                    val alamat = etEditAlamat.text.toString()
                    val jenisKelamin = selectedGender
                    val username = etEditUsername.text.toString()
                    val password = etEditPassword.text.toString()
                    val usernameLama = sharedPreferences.getUsername()

                    tempUserModel = UserModel(
                        sharedPreferences.getIdUser(),
                        nama, nomorHp, alamat, jenisKelamin,
                        username, password, "user"
                    )
                    postUpdateData(
                        sharedPreferences.getIdUser(), nama, nomorHp, alamat,
                        jenisKelamin, username, password, usernameLama
                    )
                    dialogInputan.dismiss()
                }

            }
            btnBatal.setOnClickListener {
                dialogInputan.dismiss()
            }
        }
    }

    private fun postUpdateData(
        idUser: Int,
        nama: String,
        nomorHp: String,
        alamat: String,
        jenisKelamin: String,
        username: String,
        password: String,
        usernameLama: String
    ) {
        viewModel.postUpdateProgress(
            idUser, nama, nomorHp, alamat, jenisKelamin, username, password, usernameLama
        )
    }

    private fun getUpdateData(){
        viewModel.getUpdateProgress.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading -> loading.alertDialogLoading(requireContext())
                is UIState.Failure -> setFailureUpdateData(result.message)
                is UIState.Success -> setSuccessUpdateData(result.data)
            }
        }
    }

    private fun setFailureUpdateData(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        loading.alertDialogCancel()
    }

    private fun setSuccessUpdateData(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status=="0"){
            Toast.makeText(requireContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show()
            userModel = tempUserModel
            tempUserModel = UserModel()
            setDataUser()
        } else{
            Toast.makeText(requireContext(), data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAccountActive() {
        binding.apply {
            // akun
            btnAkun.setBackgroundResource(R.drawable.bg_card_active)
            btnAkun.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            llAccount.visibility = View.VISIBLE

            // riwayat
            btnRiwayat.setBackgroundResource(R.drawable.bg_card)
            btnRiwayat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            llRiwayat.visibility = View.GONE
        }
    }

    private fun setHistoryActive() {
        binding.apply {
            // akun
            btnAkun.setBackgroundResource(R.drawable.bg_card)
            btnAkun.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
            llAccount.visibility = View.GONE

            // riwayat
            btnRiwayat.setBackgroundResource(R.drawable.bg_card_active)
            btnRiwayat.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
            llRiwayat.visibility = View.VISIBLE
        }
    }

    private fun setDataUser() {
        binding.apply {
            tvNama.text = userModel.nama
            tvNomorHp.text = userModel.nomor_hp
            tvAlamat.text = userModel.alamat
            tvJenisKelamin.text = userModel.jenis_kelamin
            tvUsername.text = userModel.username
            tvPassword.text = userModel.password
        }
    }
}