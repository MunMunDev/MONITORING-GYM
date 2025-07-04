package com.abcd.monitoring_gym.ui.fragment.user.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.monitoring_gym.adapter.PesananAdapter
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.databinding.FragmentHomeBinding
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = SharedPreferencesLogin(requireContext())
        viewModel.fetchPesanan(sharedPreferences.getIdUser())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButton()
        getPesanan()
    }

    private fun setButton() {
        binding.apply {
            btnPelatihan.setOnClickListener {

            }
            btnAgenda.setOnClickListener {

            }
            btnAkun.setOnClickListener {

            }
        }
    }

    private fun getPesanan() {
        viewModel.getPesanan.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Success-> setSuccessFetchPesanan(result.data)
                is UIState.Failure-> setFailureFetchPesanan(result.message)
            }
        }
    }

    private fun setFailureFetchPesanan(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun setSuccessFetchPesanan(data: ArrayList<PesananModel>) {
        if(data.isNotEmpty()){
            setAdapter(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapter(data: ArrayList<PesananModel>) {
        val adapterPesanan = PesananAdapter(data)
        binding.rvPesanan.apply {
            adapter = adapterPesanan
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setStartShimmer(){
        binding.apply {
            smPesanan.startShimmer()
            smPesanan.visibility = View.VISIBLE
            rvPesanan.visibility = View.GONE
        }
    }

    private fun setStopShimmer(){
        binding.apply {
            smPesanan.stopShimmer()
            smPesanan.visibility = View.GONE
            rvPesanan.visibility = View.VISIBLE
        }
    }

}