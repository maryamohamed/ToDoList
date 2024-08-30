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

class LoginFragment : Fragment() {

    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        emailLayout = view.findViewById(R.id.textInputLayout5)
        passwordLayout = view.findViewById(R.id.textInputLayout4)

        emailEditText = view.findViewById(R.id.register_email)
        passwordEditText = view.findViewById(R.id.login_password)

        loginButton = view.findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            if (validateInput()) {
                Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    private fun validateInput(): Boolean {
        var isValid = true

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
        val password = passwordEditText.text.toString()
        if (TextUtils.isEmpty(password)) {
            passwordLayout.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            passwordLayout.error = "Password must be at least 6 characters long"
            isValid = false
        } else {
            passwordLayout.error = null
        }

        return isValid
    }
}