package com.example.bai_tap_ve_nha_data_flow

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.GoogleAuthProvider

data class UserInfo (val email: String?, val username: String?)

@Composable
fun GoogleSignInButton(onSuccessfulSignedIn: (UserInfo) -> Unit, onFailureSignedIn: (Exception) -> Unit ){
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()
    val googleSignInClient = getGoogleSignInClient(context)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    val firebaseAccount = FirebaseAuth.getInstance().currentUser
                    val userInfo = UserInfo(firebaseAccount?.email, firebaseAccount?.displayName)
                    onSuccessfulSignedIn(userInfo) }
                .addOnFailureListener { exception -> onFailureSignedIn(exception) }
        }catch (e: ApiException){
            Log.e("GoogleSignIn", "Sign-in failed with ApiException: ${e.statusCode}, Message: ${e.message}")
            onFailureSignedIn(e)
        }
    }

    Button(
        onClick = {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        },
        modifier = Modifier.fillMaxWidth(0.7f).padding(vertical = 15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = BorderStroke(width = 2.dp, color = Color(0x33333333)),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            hoveredElevation = 12.dp,
            pressedElevation = 2.dp
        )
    ){
        Icon(
            painter = painterResource(R.drawable.google),
            contentDescription = "Google",
            tint = Color.Unspecified,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = "SIGN IN",
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            fontFamily = FontFamily(Font(R.font.righteous_regular)),
            modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 10.dp).padding(start = 10.dp)
        )
        Icon(
            painter = painterResource(R.drawable.login_icon),
            contentDescription = "sign in",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun LoginWithGoogleScreen(navController: NavHostController, viewModel: ResetPasswordViewModel = viewModel(), onShowBanner: ((String) -> Unit)? = null){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var bannerMessage by remember { mutableStateOf("") }
        var showBanner by remember { mutableStateOf(false) }
        // Nen o phia tren
        Box(
            modifier = Modifier.fillMaxSize(0.45f)
                .paint(
                    painter = painterResource(R.drawable.network_bg),
                    contentScale = ContentScale.Crop
                )
        ) {
            TopBanner(message = bannerMessage, show = showBanner, onDismiss = {showBanner = false})
            // Title:
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.fillMaxHeight(0.25f))
                Box(
                    modifier = Modifier.fillMaxHeight(0.85f)
                        .aspectRatio(1f)
                        .background(color = Color(0x333388EE)),
                    contentAlignment = Alignment.Center,

                    ){
                    Icon(
                        painter = painterResource(R.drawable.main_logo_removebg),
                        contentDescription = "Logo",
                        tint = Color.Unspecified,
                        modifier = Modifier.fillMaxSize(0.7f)
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Smart Task",
                    fontSize = 28.sp,
                    color = Color(0xFF55AADD),
                    fontWeight = FontWeight.Bold
                )
            }
        }
        // Below Contents:
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "an simple and effiction to-do app",
                fontSize = 14.sp,
                color = Color(0xFF55AADD)
            )
            Spacer(Modifier.fillMaxHeight(0.15f))
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Ready to explore? Log in to get started!"
                )
                Spacer(Modifier.fillMaxHeight(0.12f))
                GoogleSignInButton(
                    onSuccessfulSignedIn = { userInfo ->
                        // Get user info:
                        viewModel.setGoogleSignInInfo(userInfo)
                        // Show notification:
                        bannerMessage = "You signed in successfully!!!"
                        showBanner = true
                        navController.navigate("confirm")
                    },
                    onFailureSignedIn = {e ->
                        Log.e("GoogleSignIn", "Sign-in failed: ${e.message}", e)
                        bannerMessage = "Failed to sign in! \n${e.message}"
                        showBanner = true
                    }
                )
                TextButton(
                    onClick = {navController.navigate("forgot_password")},
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text("forgot password?", color = Color(0xFF88CCFF))
                }
            }
            Spacer(Modifier.weight(0.9f))
            Text(
                text = "© UTH Smart Task"
            )
        }
    }
}

