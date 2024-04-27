package com.nafi.sfoods.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.nafi.sfoods.core.ViewHolderBinder
import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.databinding.ItemCartBinding
import com.nafi.sfoods.databinding.ItemCheckoutBinding
import com.nafi.sfoods.utils.doneEditing
import com.nafi.sfoods.utils.toIndonesianFormat

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Cart>() {
                override fun areItemsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.menuId == newItem.menuId
                }

                override fun areContentsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return if (cartListener != null) {
            CartViewHolder(
                ItemCartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                cartListener,
            )
        } else {
            CheckoutViewHolder(
                ItemCheckoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }
}

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(data: Cart) {
        setCartData(data)
        setCartNotes(data)
        setClickListeners(data)
    }

    private fun setClickListeners(data: Cart) {
        with(binding) {
            tvCartPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(data) }
            tvCartMin.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(data) }
            ivCartDelete.setOnClickListener { cartListener?.onRemoveCartClicked(data) }
        }
    }

    private fun setCartNotes(data: Cart) {
        binding.textInputNotes.setText(data.itemNotes)
        binding.textInputNotes.doneEditing {
            binding.textInputNotes.clearFocus()
            val newItem =
                data.copy().apply {
                    itemNotes = binding.textInputNotes.text.toString()
                }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setCartData(data: Cart) {
        with(binding) {
            ivCartImage.load(data.menuImg) {
                crossfade(true)
            }
            tvCartMenuName.text = data.menuName
            tvCartMenuPrice.text = (data.itemQuantity * data.menuPrice).toIndonesianFormat()
            tvCartCounter.text = data.itemQuantity.toString()
        }
    }
}

class CheckoutViewHolder(
    private val binding: ItemCheckoutBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(data: Cart) {
        setCheckoutData(data)
    }

    private fun setCheckoutData(data: Cart) {
        with(binding) {
            ivCheckoutImage.load(data.menuImg) {
                crossfade(true)
            }
            tvCheckoutMenuName.text = data.menuName
            tvCartMenuPrice.text = data.menuPrice.toIndonesianFormat()
            "Qty : ${data.itemQuantity}".also { tvCheckoutQuantity.text = it }
        }
        binding.tvCheckoutNotes.text = data.itemNotes
    }
}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)

    fun onMinusTotalItemCartClicked(cart: Cart)

    fun onRemoveCartClicked(cart: Cart)

    fun onUserDoneEditingNotes(cart: Cart)
}
