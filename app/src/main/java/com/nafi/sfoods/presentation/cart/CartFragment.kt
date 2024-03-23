package com.nafi.sfoods.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nafi.sfoods.R
import com.nafi.sfoods.databinding.FragmentCartBinding


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


}