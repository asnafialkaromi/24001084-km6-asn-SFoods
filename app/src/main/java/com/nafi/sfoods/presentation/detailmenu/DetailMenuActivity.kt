package com.nafi.sfoods.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.cart.CartDatabaseDataSource
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.CartRepositoryImpl
import com.nafi.sfoods.data.source.local.database.AppDatabase
import com.nafi.sfoods.databinding.ActivityDetailMenuBinding
import com.nafi.sfoods.utils.GenericViewModelFactory
import com.nafi.sfoods.utils.proceedWhen
import com.nafi.sfoods.utils.toIndonesianFormat

class DetailMenuActivity : AppCompatActivity() {

    companion object {

        const val EXTRAS_DETAIL_DATA = "EXTRAS_DETAIL_DATA"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_DETAIL_DATA, menu)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDatabaseDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)

        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        onClickAction()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.menuCounterLiveData.observe(this) {
            binding.layoutBottomDetail.tvCounter.text = it.toString()
        }
        viewModel.menuPriceLiveData.observe(this) {
            binding.layoutBottomDetail.btnAddToCart.text =
                getString(R.string.text_cart_add_to_cart, it.toIndonesianFormat())
        }
    }

    private fun getIntentData() {
        intent.extras?.getParcelable<Menu>(EXTRAS_DETAIL_DATA)?.let { it ->
            binding.ivMenuImage.load(it.imgUrl) {
                crossfade(true)
            }
            binding.layoutContentDetail.tvMenuName.text = it.name
            binding.layoutContentDetail.tvPrice.text = it.price.toIndonesianFormat()
            binding.layoutContentDetail.tvRating.text = it.rating.toString()
            binding.layoutContentDetail.tvDescription.text = it.description
            binding.layoutLocation.tvDetailLocation.text = it.location
        }
    }

    private fun onClickAction() {
        binding.layoutBottomDetail.btnPlus.setOnClickListener {
            viewModel.plusCounter()
        }

        binding.layoutBottomDetail.btnMin.setOnClickListener {
            viewModel.minusCounter()
        }

        binding.layoutTopDetail.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.layoutLocation.tvDetailLocation.setOnClickListener {
            setPinLocationMap()
        }

        binding.layoutBottomDetail.btnAddToCart.setOnClickListener {
            addMenuToCart()
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutBottomDetail.btnAddToCart.isEnabled = true
                    binding.layoutBottomDetail.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.text_cart_add_menu_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    binding.layoutBottomDetail.btnAddToCart.isEnabled = false
                    binding.layoutBottomDetail.pbLoading.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.text_cart_add_menu_failed), Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.layoutBottomDetail.btnAddToCart.isEnabled = false
                    binding.layoutBottomDetail.pbLoading.isVisible = true
                    getString(R.string.text_button_empty).also { binding.layoutBottomDetail.btnAddToCart.text = it }
                }
            )
        }
    }

    private fun setPinLocationMap() {
        viewModel.mapUrlLiveData.observe(this) {
            val mapUri = Uri.parse(it)
            val intent = Intent(Intent.ACTION_VIEW, mapUri)
            startActivity(intent)
        }
    }
}