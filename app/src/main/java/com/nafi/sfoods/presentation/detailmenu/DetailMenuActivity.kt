package com.nafi.sfoods.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.nafi.sfoods.data.model.Menu
import com.nafi.sfoods.databinding.ActivityDetailMenuBinding
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

    private var qty: Int = 1
    private var amount: Double = 0.0
    private var mapUrl: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        onClickAction()
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
            "Tambah ke keranjang - ${it.price.toIndonesianFormat()}".also {
                binding.layoutBottomDetail.btnAddToCart.text = it
            }
            amount = it.price
            mapUrl = it.mapUrl
        }
    }

    private fun onClickAction() {
        binding.layoutBottomDetail.btnPlus.setOnClickListener {
            qty++
            val finalPrice: Double = qty * amount
            binding.layoutBottomDetail.tvCounter.text = qty.toString()
            binding.layoutBottomDetail.btnAddToCart.text = finalPrice.toIndonesianFormat()
            "Tambah ke Keranjang - ${finalPrice.toIndonesianFormat()}".also {
                binding.layoutBottomDetail.btnAddToCart.text = it
            }
        }

        binding.layoutBottomDetail.btnMin.setOnClickListener {
            if (qty > 1) {
                qty--
                binding.layoutBottomDetail.tvCounter.text = qty.toString()
                val finalPrice: Double = qty * amount
                binding.layoutBottomDetail.tvCounter.text = qty.toString()
                "Tambah ke Keranjang - ${finalPrice.toIndonesianFormat()}".also {
                    binding.layoutBottomDetail.btnAddToCart.text = it
                }
            }
        }

        binding.layoutTopDetail.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.layoutLocation.tvDetailLocation.setOnClickListener {
            setPinLocationMap(mapUrl)
        }
    }

    private fun setPinLocationMap(mapLink: String) {
        val mapUri = Uri.parse(mapLink)
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        startActivity(intent)
    }
}