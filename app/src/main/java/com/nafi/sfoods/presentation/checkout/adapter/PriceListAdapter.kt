package com.nafi.sfoods.presentation.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nafi.sfoods.data.model.PriceItem
import com.nafi.sfoods.databinding.ItemCheckoutSummaryBinding
import com.nafi.sfoods.utils.toIndonesianFormat

class PriceListAdapter(private val itemClick: (PriceItem) -> Unit) : RecyclerView.Adapter<PriceListAdapter.PriceItemViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<PriceItem>() {
                override fun areItemsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem,
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun insertData(data: List<PriceItem>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PriceItemViewHolder {
        val binding = ItemCheckoutSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceItemViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: PriceItemViewHolder,
        position: Int,
    ) {
        holder.bindView(dataDiffer.currentList[position])
    }

    class PriceItemViewHolder(
        private val binding: ItemCheckoutSummaryBinding,
        val itemClick: (PriceItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: PriceItem) {
            with(item) {
                binding.tvMenuTitle.text = item.name
                binding.tvMenuPrice.text = item.total.toIndonesianFormat()
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
