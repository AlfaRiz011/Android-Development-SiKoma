package com.example.sikoma.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.example.sikoma.R
import com.example.sikoma.data.remote.request.LoginBodyRequest
import com.example.sikoma.databinding.FragmentLoginBinding
import com.example.sikoma.ui.activities.HomeActivity
import com.example.sikoma.ui.activities.HomeAdminActivity
import com.example.sikoma.ui.viewmodels.AuthViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private lateinit var dataLogin: LoginBodyRequest

    private var email: String? = null
    private var password: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)


        email = arguments?.getString("email")
        password = arguments?.getString("password")

        setView()
        setOnBack()
    }


    private fun setView() {

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        binding.apply {

            inputEmail.setText(email)
            inputPassword.setText(password)

            dataLogin = LoginBodyRequest(
                inputEmail.toString(),
                inputPassword.toString()
            )
            var isValid: Boolean

            buttonLogin.setOnClickListener {
                isValid = if (inputPassword.text.toString() == password) {
                    ValidatorAuthHelper.validateInputAuth(
                        requireContext(),
                        emailInputLayout,
                        passwordInputLayout,
                        inputEmail,
                        inputPassword
                    )
                } else {
                    false
                }

                if (isValid) {
                    viewModel.login(dataLogin).observe(requireActivity()){ result ->
                        when {
                            result.status == "success" -> {
                                val destinationActivity = when (result.data?.role) {
                                    "user" -> HomeActivity::class.java
                                    "admin" -> HomeAdminActivity::class.java
                                    else -> null
                                }
                                destinationActivity?.let {
                                    startActivity(Intent(requireActivity(), it))
                                    requireActivity().finish()
                                }
                            }
                            else -> handleError(result.message?.toInt())
                        }
                    }
                }
            }

            registerPage.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                    )
                    replace(R.id.fragment_container_auth, RegisterFragment())
                    addToBackStack(null)
                }.commit()
            }
        }
    }

    private fun handleError(error: Int?) {
        when (error) {
            400 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_invalid_input)
            )

            401 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_unauthorized_401)
            )

            500 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_server_500)
            )

            503 -> ValidatorAuthHelper.showToast(
                requireContext(),
                getString(R.string.error_server_500)
            )
        }
    }

    private fun setOnBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}
