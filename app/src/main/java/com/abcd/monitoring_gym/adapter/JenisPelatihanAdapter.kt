package com.abcd.monitoring_gym.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.JenisPelatihanModel
import com.abcd.monitoring_gym.databinding.ItemListJenisPelatihanBinding
import com.abcd.monitoring_gym.utils.OnClickItem

class JenisPelatihanAdapter(
    private val listJenisPelatihan: ArrayList<JenisPelatihanModel>,
    private val onClick: OnClickItem.ClickJenisPelatihan,
) : RecyclerView.Adapter<JenisPelatihanAdapter.JenisPelatihanViewHolder>() {
    private var selectedPosition = 0

    inner class JenisPelatihanViewHolder(val binding: ItemListJenisPelatihanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JenisPelatihanViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListJenisPelatihanBinding.inflate(inflater, parent, false)
        return JenisPelatihanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JenisPelatihanViewHolder, position: Int) {
        val jenisPelatihan = listJenisPelatihan[position]
        holder.apply {
            binding.apply {
                tvJudul.text = jenisPelatihan.jenis_pelatihan
                val isSelected = position == selectedPosition

                if (isSelected) setActiveData(binding) else setInactiveData(binding)

            }
            itemView.setOnClickListener {
                selectedPosition = holder.getAdapterPosition()

                notifyDataSetChanged()
                onClick.clickJenisPelatihan(jenisPelatihan)
            }
        }
    }

    override fun getItemCount() = listJenisPelatihan.size

    fun setActiveData(binding: ItemListJenisPelatihanBinding){
        binding.apply {
            clJenisPelatihan.setBackgroundResource(R.drawable.bg_card_active)
            tvJudul.setTextColor(ContextCompat.getColor(binding.root.context, R.color.white))
        }
    }

    fun setInactiveData(binding: ItemListJenisPelatihanBinding){
        binding.apply {
            clJenisPelatihan.setBackgroundResource(R.drawable.bg_card)
            tvJudul.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
        }
    }

}