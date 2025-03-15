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
        val pattern = Regex("^[a-zA-Z0-9._%+-]+@(gmail\\.com|mahasiswa\\.upnvj\\.ac\\.id)$")
        return pattern.matches(email)
    }

    private fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    private fun validateEmailField(emailInputLayout: TextInputLayout, emailEditText: EditText): Boolean {
        val email = emailEditText.text.toString().trim()
        return if (!validateEmail(email)) {
            emailInputLayout.error = "Input Invalid"
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
        facultyInputLayout: TextInputLayout,
        facultyEditText: EditText
    ): Boolean {
        var isValid = true

        if (nameEditText.text.toString().isBlank()) {
            nameInputLayout.error = "Name cannot be empty"
            isValid = false
        } else {
            nameInputLayout.error = null
        }

        if (nimEditText.text.toString().isBlank()) {
            nimInputLayout.error = "NIM cannot be empty"
            isValid = false
        } else {
            nimInputLayout.error = null
        }

        if (prodiEditText.text.toString().isBlank()) {
            prodiInputLayout.error = "Study program cannot be empty"
            isValid = false
        } else {
            prodiInputLayout.error = null
        }

        if (facultyEditText.text.toString().isBlank()) {
            facultyInputLayout.error = "faculty cannot be empty"
            isValid = false
        } else {
            facultyInputLayout.error = null
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
        facultyLayout : TextInputLayout,
        nameInput : EditText,
        nimInput : EditText,
        prodiInput : EditText,
        facultyInput : EditText
    ): Boolean{

        val isBioValid = validateBio(nameLayout, nameInput, nimLayout, nimInput, prodiLayout, prodiInput, facultyLayout, facultyInput)

        if(!isBioValid){
            showToast(context, "Input Invalid")
        }

        return isBioValid
    }
}