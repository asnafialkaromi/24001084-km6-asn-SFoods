package com.nafi.sfoods.presentation.checkout

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.datasource.cart.CartDatabaseDataSource
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.CartRepositoryImpl
import com.nafi.sfoods.data.source.local.database.AppDatabase
import com.nafi.sfoods.databinding.ActivityCheckoutBinding
import com.nafi.sfoods.presentation.checkout.adapter.PriceListAdapter
import com.nafi.sfoods.presentation.common.adapter.CartListAdapter
import com.nafi.sfoods.presentation.dialog.DialogOrderConfirmation
import com.nafi.sfoods.utils.GenericViewModelFactory
import com.nafi.sfoods.utils.proceedWhen
import com.nafi.sfoods.utils.toIndonesianFormat

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CheckoutViewModel(rp))
    }

    private val adapterCart: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeData()
        setList()
        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutCheckoutHeader.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.layoutCheckoutFooter.btnOrder.setOnClickListener {
            viewModel.deleteAllCarts.observe(this) {
                return@observe
            }
            setDialog()
        }
    }

    private fun setDialog() {
        DialogOrderConfirmation().show(supportFragmentManager, "Show the dialog")
    }

    private fun setList() {
        binding.layoutContentCheckout.layoutCheckoutMenu.rvCheckout.adapter = adapterCart
        binding.layoutContentCheckout.layoutOrderSummary.rvShoppingSummary.adapter =
            priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContentCheckout.root.isVisible = false
                    binding.layoutCheckoutFooter.root.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContentCheckout.root.isVisible = true
                    binding.layoutCheckoutFooter.root.isVisible = true
                    result.payload?.let { (carts, priceItem, totalPrice) ->
                        adapterCart.submitData(carts)
                        binding.layoutCheckoutFooter.tvTotalPriceNumber.text =
                            totalPrice.toIndonesianFormat()
                        priceItemAdapter.insertData(priceItem)
                    }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.layoutContentCheckout.root.isVisible = false
                    binding.layoutCheckoutFooter.root.isVisible = false
                },
                doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.layoutContentCheckout.root.isVisible = false
                    binding.layoutCheckoutFooter.root.isVisible = false
                    data.payload?.let { (_, _, totalPrice) ->
                        binding.layoutCheckoutFooter.tvTotalPriceNumber.text =
                            totalPrice.toIndonesianFormat()
                    }

                }
            )
        }
    }
}