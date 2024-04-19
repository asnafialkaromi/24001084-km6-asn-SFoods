package com.nafi.sfoods.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.nafi.sfoods.data.model.Category
import com.nafi.sfoods.databinding.ItemCategoryBinding


class CategoryListAdapter(private val itemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        val itemClick: (Category) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Category) {
            with(data){
                binding.ivCategory.load(data.imgUrl) {
                    crossfade(true)
                }
                binding.tvCategory.text = data.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )


    fun insertData(data: List<Category>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return CategoryViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }
}