package com.nafi.sfoods.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.nafi.sfoods.R
import com.nafi.sfoods.databinding.ActivityLoginBinding
import com.nafi.sfoods.presentation.main.MainActivity
import com.nafi.sfoods.presentation.register.RegisterActivity
import com.nafi.sfoods.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        observeResult()
    }

    private fun setOnClickListener() {
        binding.btnLogin.setOnClickListener {
            doLogin()
        }

        binding.tvRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun observeResult() {
        loginViewModel.loginResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.text_login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnLogin.isEnabled = true
                    getString(R.string.text_button_login).also { binding.btnLogin.text = it }
                    Toast.makeText(
                        this,
                        getString(R.string.text_login_failed, it.exception?.message.orEmpty()),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnLogin.isEnabled = false
                    getString(R.string.text_button_empty).also { binding.btnLogin.text = it }
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

    private fun doLogin() {
        if (isFormValid()) {
            val email = binding.textEmail.text.toString().trim()
            val password = binding.textPassword.text.toString().trim()
            loginViewModel.doLogin(email, password)
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.textEmail.text.toString().trim()
        val password = binding.textPassword.text.toString().trim()

        return checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.tilPassword)
    }

    private fun checkEmailValidation(email: String): Boolean {
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

    private fun checkPasswordValidation(
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
}
