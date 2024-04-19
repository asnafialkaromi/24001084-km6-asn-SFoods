package com.nafi.sfoods.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.nafi.sfoods.data.datasource.cart.CartDataSource
import com.nafi.sfoods.data.datasource.cart.CartDatabaseDataSource
import com.nafi.sfoods.data.datasource.menu.MenuApiDataSource
import com.nafi.sfoods.data.datasource.menu.MenuDataSource
import com.nafi.sfoods.data.datasource.user.UserDataSource
import com.nafi.sfoods.data.repository.CartRepository
import com.nafi.sfoods.data.repository.CartRepositoryImpl
import com.nafi.sfoods.data.repository.MenuRepository
import com.nafi.sfoods.data.repository.MenuRepositoryImpl
import com.nafi.sfoods.data.repository.UserRepositoryImpl
import com.nafi.sfoods.data.source.local.database.AppDatabase
import com.nafi.sfoods.data.source.network.services.SFoodsApiService
import com.nafi.sfoods.databinding.FragmentProfileBinding
import com.nafi.sfoods.presentation.checkout.CheckoutViewModel
import com.nafi.sfoods.presentation.main.MainActivity
import com.nafi.sfoods.presentation.register.RegisterViewModel
import com.nafi.sfoods.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): ProfileViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo = UserRepositoryImpl(dataSource)
        return ProfileViewModel(repo)
    }

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
        val editTextEmail = binding.textEditEmail

        binding.layoutHeaderProfile.ivEdit.setOnClickListener {
            if (editTextUsername.isEnabled) {
                val fullName = editTextUsername.text.toString()
                val email = editTextEmail.text.toString()

                if (fullName.isNotEmpty() && email.isNotEmpty()) {
                    saveNewProfile()
                    binding.layoutHeaderProfile.ivEdit.setImageResource(R.drawable.ic_edit)
                    editTextUsername.isEnabled = false
                    editTextEmail.isEnabled = false
                } else {
                    if (fullName.isEmpty()) {
                        binding.textEditUsername.error = getString(R.string.text_username_is_empty)
                    }
                    if (email.isEmpty()) {
                        binding.textEditEmail.error = getString(R.string.text_email_is_empty)
                    }
                }
            } else {
                binding.layoutHeaderProfile.ivEdit.setImageResource(R.drawable.ic_save)
                editTextUsername.isEnabled = true
                editTextEmail.isEnabled = true
            }
        }
    }

    private fun saveNewProfile(){
        val fullName = binding.textEditUsername.text.toString()
        val email = binding.textEditEmail.text.toString()
        viewModel.updateProfile(fullName, null)
        viewModel.updateEmail(email)
    }

}