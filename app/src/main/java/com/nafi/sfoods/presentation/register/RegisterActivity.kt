package com.nafi.sfoods.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.nafi.sfoods.R
import com.nafi.sfoods.data.datasource.auth.FirebaseAuthDataSourceImpl
import com.nafi.sfoods.data.repository.UserRepositoryImpl
import com.nafi.sfoods.databinding.ActivityRegisterBinding
import com.nafi.sfoods.presentation.login.LoginActivity
import com.nafi.sfoods.utils.GenericViewModelFactory
import com.nafi.sfoods.utils.proceedWhen

class RegisterActivity : AppCompatActivity() {

    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): RegisterViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo = UserRepositoryImpl(dataSource)
        return RegisterViewModel(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        observeResult()
    }

    private fun setOnClickListener() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val fullName = binding.textFullName.text.toString().trim()
            val email = binding.textEmail.text.toString().trim()
            val password = binding.textPassword.text.toString().trim()
            viewModel.doRegister(fullName, email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val fullName = binding.textFullName.text.toString().trim()
        val email = binding.textEmail.text.toString().trim()
        val password = binding.textPassword.text.toString().trim()
        val confirmPassword = binding.textConfirmPassword.text.toString().trim()

        return checkNameIsValid(fullName) && checkEmailIsValid(email) &&
                checkPasswordIsValid(password, binding.tilPassword) &&
                checkPasswordIsValid(confirmPassword, binding.tilConfirmPassword) &&
                checkSamePassword(password, confirmPassword)

    }

    private fun checkNameIsValid(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.tilFullname.isErrorEnabled = true
            binding.tilFullname.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.tilFullname.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailIsValid(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.isErrorEnabled = true
            binding.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordIsValid(
        confirmPassword: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_less_password)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkSamePassword(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword) {
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error =
                getString(R.string.text_password_not_match)
            binding.tilConfirmPassword.isErrorEnabled = true
            binding.tilConfirmPassword.error =
                getString(R.string.text_password_not_match)
            false
        } else {
            binding.tilPassword.isErrorEnabled = false
            binding.tilConfirmPassword.isErrorEnabled = false
            true
        }
    }

    private fun observeResult() {
        viewModel.registerResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnRegister.isVisible = true
                    Toast.makeText(
                        this,
                        "Register Berhasil",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToLogin()
                },
                doOnError = {
                    binding.btnRegister.isVisible = true
                    getString(R.string.text_button_register).also { binding.btnRegister.text = it }
                    Toast.makeText(
                        this,
                        "Register Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnRegister.isEnabled = false
                    getString(R.string.text_button_empty).also { binding.btnRegister.text = it }
                }
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
