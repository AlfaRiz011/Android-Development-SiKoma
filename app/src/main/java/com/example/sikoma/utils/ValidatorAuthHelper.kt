package com.example.sikoma.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

object ValidatorAuthHelper {
    fun showToast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun validateEmail(email: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9._%+-]+@mahasiswa\\.upnvj\\.ac\\.id$")
        return pattern.matches(email)
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun validateEmailField(emailInputLayout: TextInputLayout, emailEditText: EditText): Boolean {
        val email = emailEditText.text.toString().trim()
        return if (!validateEmail(email)) {
            emailInputLayout.error = "Use official UPN Email"
            false
        } else {
            emailInputLayout.error = null
            true
        }
    }

    private fun validatePasswordField(passwordInputLayout: TextInputLayout, passwordEditText: EditText): Boolean {
        val password = passwordEditText.text.toString().trim()
        return if (!validatePassword(password)) {
            passwordInputLayout.error = "Password must 8 or more character"
            false
        } else {
            passwordInputLayout.error = null
            true
        }
    }

    private fun validateBio(
        nameInputLayout: TextInputLayout,
        nameEditText: EditText,
        nimInputLayout: TextInputLayout,
        nimEditText: EditText,
        prodiInputLayout: TextInputLayout,
        prodiEditText: EditText,
        phoneInputLayout: TextInputLayout,
        phoneEditText: EditText
    ): Boolean {
        var isValid = true

        // Validate Name
        if (nameEditText.text.toString().isBlank()) {
            nameInputLayout.error = "Name cannot be empty"
            isValid = false
        } else {
            nameInputLayout.error = null
        }

        // Validate NIM (Student ID)
        if (nimEditText.text.toString().isBlank()) {
            nimInputLayout.error = "NIM cannot be empty"
            isValid = false
        } else {
            nimInputLayout.error = null
        }

        // Validate Study Program
        if (prodiEditText.text.toString().isBlank()) {
            prodiInputLayout.error = "Study program cannot be empty"
            isValid = false
        } else {
            prodiInputLayout.error = null
        }

        // Validate Phone Number
        if (phoneEditText.text.toString().isBlank()) {
            phoneInputLayout.error = "Phone number cannot be empty"
            isValid = false
        } else {
            phoneInputLayout.error = null
        }

        return isValid
    }


    fun validateInputAuth(
        context: Context,
        emailLayout : TextInputLayout,
        passwordLayout: TextInputLayout,
        emailInput: EditText,
        passwordInput: EditText): Boolean{

        val isEmailValid = validateEmailField(emailLayout, emailInput)
        val isPasswordValid = validatePasswordField(passwordLayout, passwordInput)

        if (!isEmailValid || !isPasswordValid){
            showToast(context, "Input Invalid")
        }

        return isEmailValid && isPasswordValid
    }

    fun validateInputBio(
        context: Context,
        nameLayout : TextInputLayout,
        nimLayout : TextInputLayout,
        prodiLayout : TextInputLayout,
        phoneLayout : TextInputLayout,
        nameInput : EditText,
        nimInput : EditText,
        prodiInput : EditText,
        phoneInput : EditText
    ): Boolean{

        val isBioValid = validateBio(nameLayout, nameInput, nimLayout, nimInput, prodiLayout, prodiInput, phoneLayout, phoneInput)

        if(!isBioValid){
            showToast(context, "Input Invalid")
        }

        return isBioValid
    }
}