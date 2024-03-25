package com.nafi.sfoods.presentation.home.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.databinding.ItemMenuGridBinding
import com.nafi.sfoods.presentation.home.adapter.OnItemClickedListener
import com.nafi.sfoods.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val binding: ItemMenuGridBinding,
    private val listener: OnItemClickedListener<Menu>
) : ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(data: Menu) {
        with(data) {
            binding.ivMenuImage.load(data.imgUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = data.name
            binding.tvMenuPrice.text = data.price.toIndonesianFormat()
            binding.tvMenuRating.text = data.rating.toString()
            itemView.setOnClickListener {
                listener.onItemClicked(data)
            }
        }
    }

}