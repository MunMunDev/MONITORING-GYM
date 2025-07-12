package com.abcd.monitoring_gym.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.PelatihanModel
import com.abcd.monitoring_gym.databinding.ItemListPelatihanBinding
import com.abcd.monitoring_gym.ui.activity.user.pelatihan.detail.DetailPelatihanActivity
import com.abcd.monitoring_gym.utils.KonversiRupiah
import com.bumptech.glide.Glide

@SuppressLint("NotifyDataSetChanged")
class PelatihanAdapter(
    private val listPelatihan: ArrayList<PelatihanModel>,
) : RecyclerView.Adapter<PelatihanAdapter.PelatihanViewHolder>() {
    private val rupiah = KonversiRupiah()

    private var tempPelatihan = listPelatihan
    private var tempPelatihan2 = tempPelatihan

    @SuppressLint("NotifyDataSetChanged", "DefaultLocale")
    fun searchData(kata: String){
        val vKata = kata.lowercase().trim()
        val data = listPelatihan.filter {
            (
                it.pelatihan!!.lowercase().trim().contains(vKata)
                or
                it.deskripsi!!.lowercase().trim().contains(vKata)
                or
                it.jenis_pelatihan?.jenis_pelatihan.toString().contains(vKata)
            )
        }
        tempPelatihan = data as ArrayList<PelatihanModel>
        tempPelatihan2 = tempPelatihan
        notifyDataSetChanged()
    }

    fun searchJenisPelatihan(kata: String){
        val vKata = kata.lowercase().trim()
        val data = tempPelatihan.filter {
            (
                it.jenis_pelatihan?.jenis_pelatihan!!.lowercase().trim().contains(vKata)
            )
        }
        tempPelatihan2 = data as ArrayList<PelatihanModel>
        notifyDataSetChanged()
    }

    inner class PelatihanViewHolder(val binding: ItemListPelatihanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelatihanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListPelatihanBinding.inflate(inflater, parent, false)
        return PelatihanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PelatihanViewHolder, position: Int) {
        val pelatihan = tempPelatihan2[position]
        holder.apply {
            binding.apply {
                var hariKhusus = "Setiap ${pelatihan.hari_khusus}";
                if(pelatihan.hari_khusus == null || pelatihan.hari_khusus!!.isEmpty()){
                    hariKhusus = "-"
                }

                tvPelatihan.text = pelatihan.pelatihan
                tvJenisPelatihan.text = pelatihan.jenis_pelatihan?.jenis_pelatihan
                tvDeskripsi.text = pelatihan.deskripsi
                tvHarga.text = rupiah.rupiah(pelatihan.harga!!.trim().toLong())
                tvHariKhusus.text = hariKhusus

                Glide
                    .with(holder.itemView)
                    .load(pelatihan.gambar)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.img_main)
                    .into(ivGambar)
            }

            itemView.setOnClickListener {
                val i = Intent(itemView.context, DetailPelatihanActivity::class.java)
                i.putExtra("pelatihan", pelatihan)

                itemView.context.startActivity(i)
            }

        }

    }

    override fun getItemCount() = tempPelatihan2.size

}