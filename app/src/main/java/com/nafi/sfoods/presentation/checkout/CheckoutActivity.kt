package com.nafi.sfoods.presentation.checkout

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.datasource.cart.CartDatabaseDataSource
import com.nafi.sfoods.data.datasource.category.CategoryApiDataSource
import com.nafi.sfoods.data.datasource.category.CategoryDataSource
import com.nafi.sfoods.data.datasource.menu.MenuApiDataSource
import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.CartRepositoryImpl
import com.nafi.sfoods.data.repository.CategoryRepository
import com.nafi.sfoods.data.repository.CategoryRepositoryImpl
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.MenuRepositoryImpl
import com.nafi.sfoods.data.source.local.database.AppDatabase
import com.nafi.sfoods.data.source.network.services.SFoodsApiService
import com.nafi.sfoods.databinding.ActivityCheckoutBinding
import com.nafi.sfoods.presentation.checkout.adapter.PriceListAdapter
import com.nafi.sfoods.presentation.common.adapter.CartListAdapter
import com.nafi.sfoods.presentation.dialog.DialogOrderConfirmation
import com.nafi.sfoods.presentation.home.HomeViewModel
import com.nafi.sfoods.utils.GenericViewModelFactory
import com.nafi.sfoods.utils.proceedFlow
import com.nafi.sfoods.utils.proceedWhen
import com.nafi.sfoods.utils.toIndonesianFormat
import kotlinx.coroutines.delay

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val service = SFoodsApiService.invoke()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val cartRepository: CartRepository = CartRepositoryImpl(cartDataSource)
        val menuDataSource: MenuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(cartRepository, menuRepository))
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
            doCheckout()
        }
    }

    private fun doCheckout() {
        viewModel.checkoutCart().observe(this){
            it.proceedWhen (
                doOnSuccess = {
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = true
                    binding.layoutCheckoutFooter.pbLoading.isVisible = false
                    viewModel.deleteAllCarts()
                    setDialog()
                },
                doOnError = {
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = false
                    binding.layoutCheckoutFooter.pbLoading.isVisible = false

                },
                doOnLoading = {
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = false
                    binding.layoutCheckoutFooter.pbLoading.isVisible = true
                    getString(R.string.text_button_empty).also { binding.layoutCheckoutFooter.btnOrder.text = it }

                },
                doOnEmpty = {
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = false
                    binding.layoutCheckoutFooter.pbLoading.isVisible = false
                }
            )
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
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = true
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContentCheckout.root.isVisible = true
                    binding.layoutCheckoutFooter.root.isVisible = true
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = true
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
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = false
                },
                doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.layoutContentCheckout.root.isVisible = false
                    binding.layoutCheckoutFooter.root.isVisible = false
                    binding.layoutCheckoutFooter.btnOrder.isEnabled = false
                    data.payload?.let { (_, _, totalPrice) ->
                        binding.layoutCheckoutFooter.tvTotalPriceNumber.text =
                            totalPrice.toIndonesianFormat()
                    }
                }
            )
        }
    }
}