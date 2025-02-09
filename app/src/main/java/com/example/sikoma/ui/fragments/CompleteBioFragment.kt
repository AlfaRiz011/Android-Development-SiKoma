package com.example.sikoma.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.activityViewModels
import com.example.sikoma.R
import com.example.sikoma.data.remote.request.RegisterBodyRequest
import com.example.sikoma.databinding.FragmentCompleteBioBinding
import com.example.sikoma.ui.viewmodels.AuthViewModel
import com.example.sikoma.ui.viewmodels.factory.AuthViewModelFactory
import com.example.sikoma.utils.ValidatorAuthHelper

class CompleteBioFragment : Fragment() {

    private lateinit var binding: FragmentCompleteBioBinding

    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private var email: String? = null
    private var password: String? = null


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
        viewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        binding.apply {
            inputEmail.hint = email

            buttonDone.setOnClickListener {
                if (ValidatorAuthHelper.validateInputBio(
                        requireContext(),
                        nameLayout,
                        nimLayout,
                        prodiLayout,
                        facultyLayout,
                        inputName,
                        inputNim,
                        inputStudyProgram,
                        inputFaculty
                    )
                ) {
                    val dataRegister = RegisterBodyRequest(
                        email = email,
                        password = password,
                        fullName = inputName.text.toString(),
                        nim = inputNim.text.toString(),
                        studyProg = inputStudyProgram.text.toString(),
                        faculty = inputFaculty.text.toString(),
                        profilePic = ""
                    )

                    viewModel.register(dataRegister).observe(requireActivity()) { result ->
                        when (result.status) {
                            "success" -> {
                                val nextFragment = LoginFragment().apply {
                                    arguments = Bundle().apply {
                                        putString("email", email)
                                        putString("password", password)
                                    }
                                }

                                parentFragmentManager.beginTransaction()
                                    .setCustomAnimations(
                                        R.anim.slide_in_right,
                                        R.anim.slide_out_left,
                                        R.anim.slide_in_left,
                                        R.anim.slide_out_right
                                    )
                                    .replace(R.id.fragment_container_auth, nextFragment)
                                    .addToBackStack(null)
                                    .commit()
                            }
                            else -> handleError(result.message?.toInt())
                        }
                    }
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}