package com.nafi.sfoods.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.databinding.ItemMenuGridBinding
import com.nafi.sfoods.databinding.ItemMenuListBinding
import com.nafi.sfoods.presentation.home.viewholder.MenuGridItemViewHolder
import com.nafi.sfoods.presentation.home.viewholder.MenuListItemViewHolder
import com.nafi.sfoods.core.ViewHolderBinder

class MenuListAdapter(
    private val listener: OnItemClickedListener<Menu>,
    private val listMode: Int = MODE_GRID
) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private var dataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    )

    fun insertData(data: List<Menu>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (listMode == MODE_GRID) MenuGridItemViewHolder(
            ItemMenuGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        ) else {
            MenuListItemViewHolder(
                ItemMenuListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                ), listener
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Menu>).bind(dataDiffer.currentList[position])
    }
}

interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
}