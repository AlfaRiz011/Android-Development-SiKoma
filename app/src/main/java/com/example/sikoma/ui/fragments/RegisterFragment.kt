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
import com.example.sikoma.ui.viewmodels.factory.AuthViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext().applicationContext)
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
        binding.apply {

            buttonRegister.setOnClickListener {
                if (ValidatorAuthHelper.validateInputAuth(
                        requireContext(),
                        emailInputLayout,
                        passwordInputLayout,
                        inputEmail,
                        inputPassword
                    )
                ) {
                    val data = Register(
                        email = inputEmail.text.toString(),
                        password = inputPassword.text.toString()
                    )

                    viewModel.checkUser(data).observe(requireActivity()) { result ->
                        when (result.status) {
                            "success" -> {
                                val nextFragment = CompleteBioFragment().apply {
                                    arguments = Bundle().apply {
                                        putString("email", data.email)
                                        putString("password", data.password)
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
                                    commit()
                                }
                            }
                            else -> handleError(result.message?.toInt())
                        }
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
                    commit()
                }
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
}