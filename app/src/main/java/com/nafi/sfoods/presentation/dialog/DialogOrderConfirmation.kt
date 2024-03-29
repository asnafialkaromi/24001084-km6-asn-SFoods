package com.nafi.sfoods.presentation.dialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nafi.sfoods.databinding.FragmentDialogOrderConfirmationBinding
import com.nafi.sfoods.presentation.home.HomeFragment

class DialogOrderConfirmation : DialogFragment() {

    private lateinit var binding: FragmentDialogOrderConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogOrderConfirmationBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
    }

    private fun setOnClick() {
        binding.btnBackToHome.setOnClickListener {
            dismiss()
        }
    }

}