package com.abcd.monitoring_gym.ui.fragment.user.pelatihan

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.adapter.JenisPelatihanAdapter
import com.abcd.monitoring_gym.adapter.PelatihanAdapter
import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.databinding.FragmentPelatihanBinding
import com.abcd.monitoring_gym.utils.OnClickItem
import com.abcd.monitoring_gym.utils.network.UIState
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PelatihanFragment : Fragment() {
    private lateinit var binding: FragmentPelatihanBinding
    private val viewModel: PelatihanViewModel by viewModels()
    private lateinit var adapterJenisPelatihan: JenisPelatihanAdapter
    private lateinit var adapterPelatihan: PelatihanAdapter

    private var checkJenisPelatihanFirst = true
    private var nameJenisPelatihan = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPelatihanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTopAppBar()
        getJenisPelatihan()
        getPelatihan()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPelatihan()
    }

    private fun setTopAppBar() {
        binding.myAppBar.apply {
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    adapterPelatihan.searchData(s.toString())
                    if(!checkJenisPelatihanFirst){
                        adapterPelatihan.searchJenisPelatihan(nameJenisPelatihan)
                    }
                }

            })
        }
    }

    private fun getJenisPelatihan() {
        viewModel.getJenisPelatihan.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmerJenisPelatihan()
                is UIState.Failure-> setFailureJenisPelatihan(result.message)
                is UIState.Success-> setSuccessJenisPelatihan(result.data)
            }
        }
    }

    private fun setFailureJenisPelatihan(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStopShimmerJenisPelatihan()
    }

    private fun setSuccessJenisPelatihan(data: ArrayList<JenisPelatihanModel>) {
        setStopShimmerJenisPelatihan()
        val arrayJenisPelatihan : ArrayList<JenisPelatihanModel> = arrayListOf()
        arrayJenisPelatihan.add(JenisPelatihanModel(0, "Semua"))
        arrayJenisPelatihan.addAll(data)
        if(data.isNotEmpty()){
            setAdapterJenisPelatihan(arrayJenisPelatihan)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterJenisPelatihan(data: ArrayList<JenisPelatihanModel>) {
        adapterJenisPelatihan = JenisPelatihanAdapter(data, object : OnClickItem.ClickJenisPelatihan{
            override fun clickJenisPelatihan(jenisPelatihan: JenisPelatihanModel) {
                checkJenisPelatihanFirst = jenisPelatihan.id_jenis_pelatihan == 0
                if(checkJenisPelatihanFirst){
                    adapterPelatihan.searchJenisPelatihan("")
                } else{
                    adapterPelatihan.searchJenisPelatihan(jenisPelatihan.jenis_pelatihan!!)
                }
                nameJenisPelatihan = jenisPelatihan.jenis_pelatihan!!
            }
        })
        binding.rvJenisPelatihan.apply {
            adapter = adapterJenisPelatihan
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun getPelatihan() {
        viewModel.getPelatihan.observe(viewLifecycleOwner){result->
            when(result){
                is UIState.Loading-> setStartShimmerPelatihan()
                is UIState.Failure-> setFailurePelatihan(result.message)
                is UIState.Success-> setSuccessPelatihan(result.data)
            }
        }
    }

    private fun setFailurePelatihan(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        setStopShimmerPelatihan()
    }

    private fun setSuccessPelatihan(data: ArrayList<PelatihanModel>) {
        setStopShimmerPelatihan()
        if(data.isNotEmpty()){
            setAdapterPelatihan(data)
        } else{
            Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapterPelatihan(data: ArrayList<PelatihanModel>) {
        adapterPelatihan = PelatihanAdapter(data)
        binding.rvPelatihan.apply {
            adapter = adapterPelatihan
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setStartShimmerJenisPelatihan(){
        binding.apply {
            smJenisPelatihan.startShimmer()
            smJenisPelatihan.visibility = View.VISIBLE
            rvJenisPelatihan.visibility = View.GONE
        }
    }

    private fun setStopShimmerJenisPelatihan(){
        binding.apply {
            smJenisPelatihan.stopShimmer()
            smJenisPelatihan.visibility = View.GONE
            rvJenisPelatihan.visibility = View.VISIBLE
        }
    }

    private fun setStartShimmerPelatihan(){
        binding.apply {
            smPelatihan.startShimmer()
            smPelatihan.visibility = View.VISIBLE
            rvPelatihan.visibility = View.GONE
        }
    }

    private fun setStopShimmerPelatihan(){
        binding.apply {
            smPelatihan.stopShimmer()
            smPelatihan.visibility = View.GONE
            rvPelatihan.visibility = View.VISIBLE
        }
    }
}