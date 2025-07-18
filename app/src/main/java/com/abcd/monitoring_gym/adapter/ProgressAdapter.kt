package com.abcd.monitoring_gym.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.ProgressModel
import com.abcd.monitoring_gym.databinding.ItemListAktivitasBinding
import com.abcd.monitoring_gym.ui.activity.user.agenda.detail_agenda.DetailAgendaActivity
import com.bumptech.glide.Glide

class ProgressAdapter(
    private val listProgress: ArrayList<ProgressModel>,
    private val pelatihan: String,
    private val jenisPelatihan: String
) : RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {

    inner class ProgressViewHolder(val binding: ItemListAktivitasBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListAktivitasBinding.inflate(inflater, parent, false)
        return ProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        val progress = listProgress[position]
        holder.apply {
            binding.apply {
                tvIntruksi.text = progress.intruksi
                tvPelatih.text = progress.pelatih?.nama
                var selesai = ""
                if(progress.sudah_tercapai==0) {
                    tvSelesai.setTextColor(ContextCompat.getColor(itemView.context, R.color.danger))
                    selesai = "Belum"
                } else{
                    tvSelesai.setTextColor(ContextCompat.getColor(itemView.context, R.color.success))
                    selesai = "Sudah"
                }
                tvSelesai.text = selesai

                val videoId = searchIdUrlVideo(progress.link_youtube!!)
                val imgUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

                Glide
                    .with(holder.itemView)
                    .load(imgUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.img_main)
                    .into(ivGambar)

                if((listProgress.size-1) == position){
                    vPembatas.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailAgendaActivity::class.java)
                i.putExtra("progress", progress)
                i.putExtra("pelatihan", pelatihan)
                i.putExtra("jenis_pelatihan", jenisPelatihan)

                itemView.context.startActivity(i)
            }

        }

    }

    override fun getItemCount() = listProgress.size

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