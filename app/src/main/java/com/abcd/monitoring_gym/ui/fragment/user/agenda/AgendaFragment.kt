package com.abcd.monitoring_gym.ui.fragment.user.agenda

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.monitoring_gym.adapter.PesananAdapter
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.databinding.FragmentAgendaBinding
import com.abcd.monitoring_gym.utils.SharedPreferencesLogin
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class AgendaFragment : Fragment() {
    private lateinit var binding: FragmentAgendaBinding
    private lateinit var sharedPreferences: SharedPreferencesLogin
    private val viewModel: AgendaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = SharedPreferencesLogin(requireContext())
        viewModel.fetchPesanan(sharedPreferences.getIdUser())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgendaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPesanan()
    }

    private fun getPesanan() {
        viewModel.getPesanan.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmer()
                is UIState.Failure-> setFailurePesanan(result.message)
                is UIState.Success-> setSuccessPesanan(result.data)
            }
        }
    }

    private fun setFailurePesanan(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStopShimmer()
    }

    private fun setSuccessPesanan(data: ArrayList<PesananModel>) {
        if(data.isNotEmpty()){
            setAdapterPesanan(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
        setStopShimmer()
    }

    private fun setAdapterPesanan(data: ArrayList<PesananModel>) {
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