package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.example.sikoma.R
import com.example.sikoma.data.models.Register
import com.example.sikoma.data.remote.request.OtpBodyRequest
import com.example.sikoma.databinding.FragmentRegisterBinding
import com.example.sikoma.ui.viewmodels.AuthViewModel
import com.example.sikoma.ui.viewmodels.factory.ViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        setView()
        setOnBack()
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

    private fun setView() {

        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        binding.apply {

            buttonRegister.setOnClickListener {
                val isValid = ValidatorAuthHelper.validateInputAuth(
                    requireContext(),
                    emailInputLayout,
                    passwordInputLayout,
                    inputEmail,
                    inputPassword
                )

                val dataReqOTP = Register(
                    email = inputEmail.toString(),
                    password = inputPassword.toString()
                )

                if (isValid) {
                    viewModel.requestOTP(dataReqOTP).observe(requireActivity()) {
                        when {
                            it.status == "success" -> {
                                otpInclude.layoutOtp.visibility = View.VISIBLE
                            }

                            else -> handleError(it.message?.toInt())
                        }
                    }
                }
            }

            otpInclude.buttonSendOtp.setOnClickListener {

                val otpString = otpInclude.pinview.text.toString()
                val otpInt = otpString.toIntOrNull()

                val dataVerifyOTP = OtpBodyRequest(
                    otp = otpInt,
                    email = inputEmail.toString()
                )

                viewModel.verifyOTP(dataVerifyOTP).observe(requireActivity()) {
                    when {
                        it.status == "success" -> {
                            val nextFragment = CompleteBioFragment().apply {
                                arguments = Bundle().apply {
                                    putString("email", inputEmail.text.toString())
                                    putString("password", inputPassword.text.toString())
                                }
                            }

                            parentFragmentManager.beginTransaction().apply {
                                setCustomAnimations(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left,
                                    R.anim.slide_in_left,
                                    R.anim.slide_out_right
                                )
                                replace(R.id.fragment_container_auth, nextFragment)
                                addToBackStack(null)
                            }.commit()
                        }

                        else -> handleError(it.message?.toInt())
                    }
                }
            }

            loginPage.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_in_left,
                        R.anim.slide_out_right,
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                    replace(R.id.fragment_container_auth, LoginFragment())
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}