package com.training.todolist
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterFragment : Fragment() {

    private lateinit var firstNameLayout: TextInputLayout
    private lateinit var secondNameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var secondNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var registerButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        firstNameLayout = view.findViewById(R.id.textInputLayout)
        secondNameLayout = view.findViewById(R.id.textInputLayout2)
        emailLayout = view.findViewById(R.id.textInputLayout3)
        passwordLayout = view.findViewById(R.id.textInputLayout4)

        firstNameEditText = view.findViewById(R.id.register_firstname)
        secondNameEditText = view.findViewById(R.id.register_secondname)
        emailEditText = view.findViewById(R.id.register_email)
        passwordEditText = view.findViewById(R.id.login_password)

        registerButton = view.findViewById(R.id.loginButton)

        registerButton.setOnClickListener {
            validateInput()
        }

        return view
    }

    private fun validateInput() {
        var isValid = true

        // Validate First Name
        if (TextUtils.isEmpty(firstNameEditText.text)) {
            firstNameLayout.error = "First name is required"
            isValid = false
        } else {
            firstNameLayout.error = null
        }

        // Validate Second Name
        if (TextUtils.isEmpty(secondNameEditText.text)) {
            secondNameLayout.error = "Second name is required"
            isValid = false
        } else {
            secondNameLayout.error = null
        }

        // Validate Email
        val email = emailEditText.text.toString()
        if (TextUtils.isEmpty(email)) {
            emailLayout.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Enter a valid email"
            isValid = false
        } else {
            emailLayout.error = null
        }

        // Validate Password
        if (TextUtils.isEmpty(passwordEditText.text)) {
            passwordLayout.error = "Password is required"
            isValid = false
        } else if (passwordEditText.text!!.length < 6) {
            passwordLayout.error = "Password must be at least 6 characters long"
            isValid = false
        } else {
            passwordLayout.error = null
        }

        // If everything is valid
        if (isValid) {
            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show()
            // Create Intent and pass the first name
            val intent = Intent(activity, HomeActivity::class.java)
            intent.putExtra("firstName", firstNameEditText.text.toString())
            startActivity(intent)

            // Optional: Clear the form fields after successful registration
            clearFormFields()
        }
    }

    private fun clearFormFields() {
        firstNameEditText.setText("")
        secondNameEditText.setText("")
        emailEditText.setText("")
        passwordEditText.setText("")
    }
}
