package com.nafi.sfoods.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.nafi.sfoods.R
import com.nafi.sfoods.databinding.FragmentProfileBinding
import com.nafi.sfoods.presentation.main.MainActivity
import com.nafi.sfoods.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickActionSave()
        setOnClickListener()
        getDataUser()
    }

    private fun getDataUser() {
        val userData = FirebaseAuth.getInstance().currentUser
        userData?.let {
            binding.textEditEmail.setText(userData.email)
            binding.textEditUsername.setText(userData.displayName)
        }
    }

    private fun setOnClickListener() {
        binding.tvLogout.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(
                requireContext(),
                getString(R.string.text_notif_logout),
                Toast.LENGTH_SHORT
            ).show()
            navigateToMain()
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    private fun setClickActionSave() {
        val editTextUsername = binding.textEditUsername

        binding.layoutHeaderProfile.ivEdit.setOnClickListener {
            if (editTextUsername.isEnabled) {
                val fullName = editTextUsername.text.toString()

                if (fullName.isNotEmpty()) {
                    doUpdate()
                    observeResult()
                    binding.layoutHeaderProfile.ivEdit.setImageResource(R.drawable.ic_edit)
                    editTextUsername.isEnabled = false
                } else {
                    if (fullName.isEmpty()) {
                        binding.textEditUsername.error = getString(R.string.text_username_is_empty)
                    }
                }
            } else {
                binding.layoutHeaderProfile.ivEdit.setImageResource(R.drawable.ic_save)
                editTextUsername.isEnabled = true
            }
        }
    }

    private fun doUpdate() {
        val fullName = binding.textEditUsername.text.toString().trim()
        profileViewModel.updateProfile(fullName, null)
    }

    private fun observeResult() {
        profileViewModel.updateProfileResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_success_update_name),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        "${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.tvError.text = it.exception.toString()
                }
            )
        }
    }
}