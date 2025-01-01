package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.example.sikoma.R
import com.example.sikoma.data.models.Akun
import com.example.sikoma.databinding.FragmentCompleteBioBinding
import com.example.sikoma.utils.ValidatorAuthHelper
import javax.xml.validation.Validator

class CompleteBioFragment : Fragment() {

    private lateinit var binding: FragmentCompleteBioBinding

    private var email : String? = null
    private var password : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompleteBioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        email = arguments?.getString("email")
        password = arguments?.getString("password")

        setView()
    }

    private fun setView() {
        binding.apply{
            inputEmail.hint = email

            buttonDone.setOnClickListener{

                val isValid = ValidatorAuthHelper.validateInputBio(
                    requireContext(),
                    nameLayout,
                    nimLayout,
                    prodiLayout,
                    phoneLayout,
                    inputName,
                    inputNim,
                    inputStudyProgram,
                    inputPhone
                )

                if(isValid){
                    Akun(
                        email = email,
                        password = password,
                        name = inputName.text.toString(),
                        nim = inputNim.text.toString(),
                        prodi = inputStudyProgram.text.toString(),
                        phone = inputPhone.text.toString()
                    )

                    val nextFragment = LoginFragment().apply {
                        arguments = Bundle().apply {
                            putString("email",email)
                            putString("password",password)
                            putString("name",inputName.text.toString())
                            putString("nim",inputNim.text.toString())
                            putString("prodi",inputStudyProgram.text.toString())
                            putString("phone",inputPhone.text.toString())
                        }
                    }

                    Toast.makeText(requireContext(), "Register Complete", Toast.LENGTH_SHORT).show()

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
                    Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}