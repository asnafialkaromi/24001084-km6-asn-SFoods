package com.nafi.sfoods.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.nafi.sfoods.R
import com.nafi.sfoods.data.model.Cart
import com.nafi.sfoods.databinding.FragmentCartBinding
import com.nafi.sfoods.presentation.checkout.CheckoutActivity
import com.nafi.sfoods.presentation.common.adapter.CartListAdapter
import com.nafi.sfoods.presentation.common.adapter.CartListener
import com.nafi.sfoods.utils.hideKeyboard
import com.nafi.sfoods.utils.proceedWhen
import com.nafi.sfoods.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    private val cartViewModel: CartViewModel by viewModel()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(
            object : CartListener {
                override fun onPlusTotalItemCartClicked(cart: Cart) {
                    cartViewModel.increaseCart(cart)
                }

                override fun onMinusTotalItemCartClicked(cart: Cart) {
                    cartViewModel.decreaseCart(cart)
                }

                override fun onRemoveCartClicked(cart: Cart) {
                    cartViewModel.removeCart(cart)
                }

                override fun onUserDoneEditingNotes(cart: Cart) {
                    cartViewModel.setCartNotes(cart)
                    hideKeyboard()
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
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
        cartViewModel.getAllCarts().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoadingState.isVisible = true
                    binding.layoutContentState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.layoutCartFooter.root.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutContentState.root.isVisible = false
                    binding.layoutContentState.pbLoadingState.isVisible = false
                    binding.layoutContentState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    binding.layoutCartFooter.root.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.layoutCartFooter.tvTotalPriceNumber.text =
                            totalPrice.toIndonesianFormat()
                    }
                },
                doOnError = {
                    binding.layoutContentState.root.isVisible = false
                    binding.layoutContentState.pbLoadingState.isVisible = false
                    binding.layoutContentState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                    binding.layoutCartFooter.root.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutContentState.root.isVisible = true
                    binding.layoutContentState.pbLoadingState.isVisible = false
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
                },
            )
        }
    }

    private fun setList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }
}
