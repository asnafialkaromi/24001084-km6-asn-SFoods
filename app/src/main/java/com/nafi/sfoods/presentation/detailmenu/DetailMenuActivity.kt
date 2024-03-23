package com.nafi.sfoods.presentation.detailmenu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nafi.sfoods.R
import com.nafi.sfoods.databinding.ActivityDetailMenuBinding

class DetailMenuActivity : AppCompatActivity() {

    private val binding : ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}