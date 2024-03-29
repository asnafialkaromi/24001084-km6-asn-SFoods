package com.nafi.sfoods.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.datasource.cart.CartDatabaseDataSource
import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.CartRepositoryImpl
import com.nafi.sfoods.data.source.local.database.AppDatabase
import com.nafi.sfoods.databinding.FragmentCartBinding
import com.nafi.sfoods.presentation.checkout.CheckoutActivity
import com.nafi.sfoods.presentation.common.adapter.CartListAdapter
import com.nafi.sfoods.presentation.common.adapter.CartListener
import com.nafi.sfoods.utils.GenericViewModelFactory
import com.nafi.sfoods.utils.hideKeyboard
import com.nafi.sfoods.utils.proceedWhen
import com.nafi.sfoods.utils.toIndonesianFormat


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModels {
        val db = AppDatabase.getInstance(requireContext())
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CartViewModel(rp))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList()
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutCartFooter.btnOrder.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoading.isVisible = true
                    binding.layoutContentState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.layoutCartFooter.root.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutContentState.root.isVisible = false
                    binding.layoutContentState.pbLoading.isVisible = false
                    binding.layoutContentState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    binding.layoutCartFooter.root.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.layoutCartFooter.tvTotalPriceNumber.text = totalPrice.toIndonesianFormat()
                    }
                },
                doOnError = {
                    binding.layoutContentState.root.isVisible = false
                    binding.layoutContentState.pbLoading.isVisible = false
                    binding.layoutContentState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                    binding.layoutCartFooter.root.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoading.isVisible = false
                    binding.layoutContentState.tvError.isVisible = true
                    binding.layoutContentState.tvError.text = getString(R.string.text_cart_empty)
                    binding.rvCart.isVisible = false
                    binding.layoutCartFooter.root.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.layoutCartFooter.tvTotalPriceNumber.text =
                            totalPrice.toIndonesianFormat()
                        binding.layoutCartFooter.btnOrder.isEnabled = false
                    }
                }
            )
        }
    }

    private fun setList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }
}