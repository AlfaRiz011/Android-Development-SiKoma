package com.example.sikoma.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.sikoma.R
import com.example.sikoma.databinding.FragmentLoginBinding
import com.example.sikoma.ui.activities.HomeActivity
import com.example.sikoma.utils.ValidatorAuthHelper

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private var email : String? = null
    private var password : String? = null
    private var name : String? = null
    private var nim : String? = null
    private var prodi : String? = null
    private var phone : String? = null

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
        name = arguments?.getString("name")
        nim = arguments?.getString("nim")
        prodi = arguments?.getString("prodi")
        phone = arguments?.getString("phone")

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

            inputEmail.setText(email)
            inputPassword.setText(password)

            var isValid : Boolean

            buttonLogin.setOnClickListener {
                isValid = if(inputPassword.text.toString() == password){
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
                    Toast.makeText(
                        requireContext(),
                        "Email: $email\nPassword: $password\nName: $name\nNIM: $nim\nProdi: $prodi\nPhone: $phone",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_SHORT).show()
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
}