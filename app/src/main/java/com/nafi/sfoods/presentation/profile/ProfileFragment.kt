package com.nafi.sfoods.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nafi.sfoods.R
import com.nafi.sfoods.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickActionSave()
    }

    private fun setClickActionSave() {
        val editTextUsername = binding.textEditUsername
        val editTextEmail = binding.textEditEmail
        val editTextPhoneNumber = binding.textEditPhoneNumber

        binding.layoutHeaderProfile.ivEdit.setOnClickListener {
            if (editTextUsername.isEnabled) {
                val username = editTextUsername.text.toString()
                val email = editTextEmail.text.toString()
                val phoneNumber = editTextPhoneNumber.text.toString()

                if (username.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty()) {
                    binding.layoutHeaderProfile.ivEdit.setImageResource(R.drawable.ic_edit)
                    editTextUsername.isEnabled = false
                    editTextEmail.isEnabled = false
                    editTextPhoneNumber.isEnabled = false
                } else {
                    if (username.isEmpty()) {
                        binding.textEditUsername.error = "Username tidak boleh kosong"
                    }
                    if (email.isEmpty()) {
                        binding.textEditEmail.error = "Email tidak boleh kosong"
                    }
                    if (phoneNumber.isEmpty()) {
                        binding.textEditPhoneNumber.error = "No. Telepon tidak boleh kosong"
                    }
                }
            } else {
                // Edit action
                binding.layoutHeaderProfile.ivEdit.setImageResource(R.drawable.ic_save)
                editTextUsername.isEnabled = true
                editTextEmail.isEnabled = true
                editTextPhoneNumber.isEnabled = true
            }
        }
    }
}