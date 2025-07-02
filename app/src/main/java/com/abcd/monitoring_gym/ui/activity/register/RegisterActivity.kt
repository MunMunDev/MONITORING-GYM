package com.abcd.monitoring_gym.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.ResponseModel
import com.abcd.monitoring_gym.databinding.ActivityRegisterBinding
import com.abcd.monitoring_gym.ui.activity.login.LoginActivity
import com.abcd.monitoring_gym.utils.LoadingAlertDialog
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var selectedGender: String? = null
    @Inject
    lateinit var loading: LoadingAlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
        getRegister()
    }

    private fun setButton() {
        binding.apply {
            ivBack.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            btnRegistrasi.setOnClickListener {
                if(validateData()){
                    val nama = etNama.text.toString()
                    val nomorHp = etNomorHp.text.toString()
                    val alamat = etAlamat.text.toString()
                    val username = etUsername.text.toString()
                    val password = etPassword.text.toString()

                    viewModel.postRegister(
                        nama, nomorHp, alamat, username, password
                    )
                }
            }
            // choose Gender
            rgGender.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbLakiLaki -> selectedGender = "Laki-laki"
                    R.id.rbPerempuan -> selectedGender = "Perempuan"
                }
            }
        }
    }

    private fun validateData(): Boolean {
        binding.apply {
            if (etNama.text.toString().isEmpty()) {
                etNama.error = "Nama harus diisi"
                return false
            }
            if (etNomorHp.text.toString().isEmpty()) {
                etNomorHp.error = "Nomor HP harus diisi"
                return false
            }
            if (etAlamat.text.toString().isEmpty()) {
                etAlamat.error = "Alamat harus diisi"
                return false
            }
            if (selectedGender == null) {
                Toast.makeText(this@RegisterActivity, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
                return false
            }
            if (etUsername.text.toString().isEmpty()) {
                etUsername.error = "Username harus diisi"
                return false
            }
            if (etPassword.text.toString().isEmpty()) {
                etPassword.error = "Password harus diisi"
                return false
            }
        }
        return true
    }

    private fun getRegister() {
        viewModel.getRegister().observe(this@RegisterActivity){result->
            when(result){
                is UIState.Loading-> loading.alertDialogLoading(this@RegisterActivity)
                is UIState.Success-> setSuccessPostRegister(result.data)
                is UIState.Failure-> setFailurePostRegister(result.message)
            }
        }
    }

    private fun setSuccessPostRegister(data: ResponseModel) {
        loading.alertDialogCancel()
        if(data.status == "0"){
            Toast.makeText(this@RegisterActivity, "Berhasil Registrasi", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        } else{
            Toast.makeText(this@RegisterActivity, data.message_response, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFailurePostRegister(message: String) {
        loading.alertDialogCancel()
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }

}