package com.abcd.monitoring_gym.adapter

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.PesananModel
import com.abcd.monitoring_gym.data.model.ProgressModel
import com.abcd.monitoring_gym.databinding.ItemListAgendaBinding
import com.abcd.monitoring_gym.utils.OnClickItem
import com.bumptech.glide.Glide

class PesananAdapter(
    private val listPesanan: ArrayList<PesananModel>
) : RecyclerView.Adapter<PesananAdapter.PesananViewHolder>() {

    inner class PesananViewHolder(val binding: ItemListAgendaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListAgendaBinding.inflate(inflater, parent, false)
        return PesananViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PesananViewHolder, position: Int) {
        val progress = listPesanan[position]
        holder.apply {
            binding.apply {
                tvPelatihan.text = progress.tPelatihan
                setAdapterPesanan(progress.tPelatihan!!, progress.jenis_pelatihan!!, progress.progress!!, rvProgress)

                clHeader.setOnClickListener {
                    if(llBody.visibility == View.GONE){
                        TransitionManager.beginDelayedTransition(llBody, AutoTransition())
                        llBody.visibility = View.VISIBLE
                        arrowHeader.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                    else if(llBody.visibility == View.VISIBLE){
                        TransitionManager.beginDelayedTransition(llBody, AutoTransition())
                        llBody.visibility = View.GONE
                        arrowHeader.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24)
                    }
                }
            }

        }

    }

    override fun getItemCount() = listPesanan.size

    private fun setAdapterPesanan(pelatihan:String, jenisPelatihan:String, data:ArrayList<ProgressModel>, rvProgress: RecyclerView){
        val adapterProgress = ProgressAdapter(data, pelatihan, jenisPelatihan)

        rvProgress.apply {
            adapter = adapterProgress
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

//        val adapterProgress = ProgressAdapter(data, object: OnClickItem.ClickProgress{
//            override fun clickProgress(progress: ProgressModel) {
//
//            }
//
//        } )
//        rvProgress.apply {
//            adapter = adapterProgress
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        }

    }

    private fun searchIdUrlVideo(urlVideo: String): String {
        var url = ""
        try {
            val arrayUrlIdVideo = urlVideo.split("v=")
            url = arrayUrlIdVideo[1]

            try {
                val arraySearchUrlSymbol = url.split("&")
                url = arraySearchUrlSymbol[0]
            } catch (_: Exception){
                url = arrayUrlIdVideo[1]
            }
        } catch (ex: Exception){
            try {
                val arrayUrlIdVideo = urlVideo.split("si=")
                url = arrayUrlIdVideo[1]
            } catch (ex: Exception){
                url = "0"
            }
        }
        return url
    }
}