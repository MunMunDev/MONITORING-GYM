package com.abcd.monitoring_gym.ui.fragment.user.akun

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.UserModel
import com.abcd.monitoring_gym.databinding.FragmentAkunBinding
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin

class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding
    private val viewModel: AkunViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private lateinit var userModel: UserModel

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

            }
            btnLogout.setOnClickListener {

            }
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