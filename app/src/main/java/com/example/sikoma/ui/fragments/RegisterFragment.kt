package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.sikoma.R
import com.example.sikoma.data.models.Akun
import com.example.sikoma.databinding.FragmentLoginBinding
import com.example.sikoma.databinding.FragmentRegisterBinding
import com.example.sikoma.utils.ValidatorAuthHelper

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

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
            buttonRegister.setOnClickListener{
                val isValid = ValidatorAuthHelper.validateInputAuth(
                    requireContext(),
                    emailInputLayout,
                    passwordInputLayout,
                    inputEmail,
                    inputPassword
                )

                if(isValid){
                    otpInclude.layoutOtp.visibility = View.VISIBLE
                }
            }

            otpInclude.buttonSendOtp.setOnClickListener{
                if(otpInclude.pinview.text.toString() == "1234"){
                    val nextFragment = CompleteBioFragment().apply {
                        arguments = Bundle().apply {
                            putString("email", inputEmail.text.toString())
                            putString("password",inputPassword.text.toString())
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
                } else {
                    Toast.makeText(requireContext(), "Incorrect OTP", Toast.LENGTH_SHORT).show()
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
}