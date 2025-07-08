package com.abcd.monitoring_gym.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.abcd.monitoring_gym.R
import com.abcd.monitoring_gym.data.model.ProgramModel
import com.abcd.monitoring_gym.databinding.ItemListPelatihBinding
import com.abcd.monitoring_gym.utils.OnClickItem

class ProgramAdapter(
    private val listProgram: ArrayList<ProgramModel>,
    private val onClick: OnClickItem.ClickProgram
) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {
    private var selectedPosition = 0

    inner class ProgramViewHolder(val binding: ItemListPelatihBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListPelatihBinding.inflate(inflater, parent, false)
        return ProgramViewHolder(binding)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = listProgram[position]
        holder.apply {
            binding.apply {
                tvPelatih.text = program.pelatih?.nama
                tvJenisKelamin.text = program.pelatih?.jenis_kelamin

                val isSelected = position == selectedPosition

                if (isSelected) setActiveData(binding, program) else setInactiveData(binding)
            }
            itemView.setOnClickListener {
                selectedPosition = holder.adapterPosition
                notifyDataSetChanged()
            }
        }

    }

    override fun getItemCount() = listProgram.size

    private fun setActiveData(binding: ItemListPelatihBinding, data: ProgramModel){
        binding.apply {
            llPelatih.setBackgroundResource(R.drawable.bg_card_active_pelatih)
            onClick.clickProgram(data)
        }
    }

    private fun setInactiveData(binding: ItemListPelatihBinding){
        binding.apply {
            llPelatih.setBackgroundResource(R.drawable.bg_card)
        }
    }

}