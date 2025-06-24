package com.example.bai_tap_ve_nha_data_flow

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase



class ResetPasswordViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set
    var verifyCode by mutableStateOf("222222")
        private set
    var password by mutableStateOf("")
        private set
    var username by mutableStateOf("")
        private set

    private val functions = Firebase.functions

    // Set user info from Goole Sign in:
    fun setGoogleSignInInfo(userInfo: UserInfo){
        email = userInfo.email ?: ""
        username = userInfo.username ?: "User${((10000..99999).random())}"
    }

    // Send verify code to email:
    fun sendVerifyCodeToEmail(emailInput: String){
        Log.d("VerifyCode", "🚀 emailInput = $emailInput")  // thêm dòng này
        if (emailInput.isBlank()) {
            Log.e("VerifyCode", "❌ Email input is blank!")
            return
        }
        email = emailInput
        verifyCode = generateVerifyCode()

        val data = hashMapOf<String, Any>(
            "email" to emailInput,
            "code" to verifyCode
        )

        Log.d("VerifyCode", "🔥 Sending data: $data") // ➕ In log kiểm tra


        functions
            .getHttpsCallable("sendVerificationEmail")
            .call(data)
            .addOnSuccessListener {
                Log.d("VerifyCode", "Verify code ${verifyCode} email was sent to: ${emailInput}") // gợi ý dùng debug để dễ thấy
            }
            .addOnFailureListener {e ->
                Log.e("VerifyCode", "Failed to send to email: ${e.message}")
            }
        println("Mã xác minh là: $verifyCode")
    }

    fun SetEmail(email: String){
        this.email = email
    }

    fun SetPassword(pwd: String){
        password = pwd
    }

    fun SetUsername(user: String = "LapTruong"){
        username = user
    }

    // Generate verify code:
    fun generateVerifyCode(): String {
        return (100000..999999).random().toString()
    }
}