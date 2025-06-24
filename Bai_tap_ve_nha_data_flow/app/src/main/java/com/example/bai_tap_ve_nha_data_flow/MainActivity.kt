package com.example.bai_tap_ve_nha_data_flow

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bai_tap_ve_nha_data_flow.ui.theme.Bai_tap_ve_nha_data_flowTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProfileScreen(rememberNavController())
}

@Composable
fun MainApp(){
    var navController = rememberNavController()
    var viewModel: ResetPasswordViewModel = viewModel()

    var bannerMessage by remember { mutableStateOf("") }
    var showBanner by remember { mutableStateOf(false) }

    val showTopBanner: (String) -> Unit = {message ->
        bannerMessage = message
        showBanner = true
    }

    Box(Modifier.fillMaxSize()){
        TopBanner(message = bannerMessage, show = showBanner, onDismiss = {showBanner = false})

        NavHost(navController = navController, startDestination = "login_with_google"){
            composable("login_with_google") { LoginWithGoogleScreen(navController, viewModel) }
            composable("forgot_password") { ForgotPasswordScreen(navController, viewModel) }
            composable("verify") { VerificationScreen(navController, viewModel) }
            composable("create_password") { CreateNewPasswordScreen(navController, viewModel)}
            composable("confirm") { ConfirmScreen(navController, viewModel)}
        }
    }
}

@Composable
fun ForgotPasswordScreen(navController: NavHostController, viewModel: ResetPasswordViewModel){

    var email by remember { mutableStateOf("") }

    val isCorrectEmail by remember {
        derivedStateOf {
            email == viewModel.email && isValidEmail(email)
        }
    }

    FrameScreen (
        appTitle = null,
        pageTitle = "Forgot Password?",
        description = "",
        enableNextButton = isCorrectEmail,
        navController = navController,
        nextNavigation = "verify",
        nextButtonOnClick = { if (isValidEmail(email)) {
            Log.d("VerifyCode", "Passed ${email} successfully!")
            viewModel.sendVerifyCodeToEmail(email)
        } else {
            Log.e("VerifyCode", "❌ Invalid email: $email")
        }},
        enabledBackButton = isValidEmail(viewModel.email)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.SetEmail(email)
                },
                label = {
                    Row{
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "email Input",
                            modifier = Modifier.padding(end = 5.dp),
                            tint = Color(0x55555555)
                        )
                        Text("email")
                    }
                }
            )
            // Error Warning
            if (email.isNotBlank() && !isValidEmail(email)){
                Text(
                    text = "Your email is Invalid! ",
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 10.dp)
                )
            }

        }
    }
}
fun isValidEmail(email: String): Boolean {
    var regex = Regex("^[a-zA-Z][a-zA-Z0-9_]+@[a-z_]+([.][a-z]{2,})+$")
    return regex.matches(email)
}

@Composable
fun VerificationScreen(navController: NavHostController, viewModel: ResetPasswordViewModel = viewModel()){
    var focusRequesters = List(6) { FocusRequester() }
    var code = List(6) { mutableStateOf("") }

    val isMatched by remember {
        derivedStateOf {
            code.joinToString("") { it.value } == viewModel.verifyCode
        }
    }

    FrameScreen(pageTitle = "Verify Code", navController = navController, enableNextButton = isMatched , prevNavigation = "forgot_password", nextNavigation = "create_password") {
        Row(
            modifier = Modifier.padding(top = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ){
            code.forEachIndexed { index, state ->
                OutlinedTextField(
                    value = state.value,
                    onValueChange = { value ->
                        if( value.length <= 1 && (value.isEmpty() || value.all { it.isDigit() })){
                            state.value = value
                            if(value.isNotEmpty() && index < 5){
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    modifier = Modifier
                        .width(50.dp)
                        .border(width = 2.dp, color = Color(0xFF00FFDD), RoundedCornerShape(percent = 30))
                        .focusRequester(focusRequesters[index])
                        .focusProperties{
                            next = if (index < 5) focusRequesters[index + 1] else FocusRequester.Default
                            previous = if (index > 0) focusRequesters[index - 1] else FocusRequester.Default
                        },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(percent = 30),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            if (isMatched) navController.navigate("create_password")
        }
    }
}

@Composable
fun CreateNewPasswordScreen(navController: NavHostController, viewModel: ResetPasswordViewModel = viewModel()){
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    // Visible Toggle State:
    var passwordVisible by remember { mutableStateOf(false) }
    // Password Validation:
    var passwordStrong = passwordIsStrong(password)
    var passwordMatches = passwordConfirm == password

    var passwordValid = passwordStrong && passwordMatches

    FrameScreen( pageTitle = "Create New Password", enableNextButton = passwordValid, navController = navController, prevNavigation = "verify", nextNavigation = "confirm") {
        OutlinedTextField(
            value = password,
            onValueChange = {pass ->
                password = pass
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Confirm password",
                    tint = Color(0x55555555)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible}
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "password visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = { Text("password")},
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        if (!passwordStrong){
            Text(
                text = "Your password needs at least 8 letters and has to contain uppercase and lowercase letter, digit and special symbol ",
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp)
            )
        }

        OutlinedTextField(
            value = passwordConfirm,
            onValueChange = {pass ->
                passwordConfirm = pass
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Confirm password",
                    tint = Color(0x55555555)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible}
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "password visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = { Text("Confirm password")},
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        if (!passwordMatches) {
            Text(
                text = "Your confirm password is not matched ",
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp)
            )
        }

        if (passwordValid) {
            viewModel.SetPassword(password)
            viewModel.SetUsername()
        }
    }
}
fun passwordIsStrong(password: String): Boolean {
    var regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\$%^&*()_+=\\-{}|:;\"'<>,.?/])(?=.*[0-9]).{8,}$")
    return regex.matches(password)
}

@Composable
fun ConfirmScreen(navController: NavHostController, viewModel: ResetPasswordViewModel = viewModel()){
    var usernameInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("")}
    var emailInput by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    val informationMatch: Boolean by remember {
        derivedStateOf {
            usernameInput == viewModel.username && passwordInput == viewModel.password && emailInput == viewModel.email
        }
    }

    FrameScreen(pageTitle = "Confirm", enableNextButton = false /*informationMatch*/, navController = navController, prevNavigation = "create_password") {

        // Email:
        OutlinedTextField(
            value = emailInput,
            onValueChange = {mail ->
                emailInput = mail
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "email",
                    tint = Color(0x55555555)
                )
            },
            placeholder = { Text("email")},
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        if (emailInput.isNotBlank() && emailInput.trim().lowercase() != viewModel.email.trim().lowercase()){
            Text(
                text = "Your email is incorrect! ",
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp)
            )
        }

        // username:
        OutlinedTextField(
            value = usernameInput,
            onValueChange = {name ->
                usernameInput = name
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Confirm password",
                    tint = Color(0x55555555)
                )
            },
            placeholder = { Text("username")},
            modifier = Modifier.fillMaxWidth(0.9f).padding(vertical = 20.dp)
        )
        if (usernameInput.isNotBlank() &&
            usernameInput.trim().lowercase() != viewModel.username.trim().lowercase()){
            Text(
                text = "Your username is incorrect",
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp)
            )
        }

        // Password:
        OutlinedTextField(
            value = passwordInput,
            onValueChange = {pass ->
                passwordInput = pass
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Confirm password",
                    tint = Color(0x55555555)
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible}
                ) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "password visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            placeholder = { Text("password")},
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        if (passwordInput != viewModel.password){
            Text(
                text = "Your password is incorrect",
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp)
            )
        }
    }
}


//////////////////////////////////////////////////******
//                                                      Material ******////////////////////////////////////////

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrameScreen(
    appTitle: String? = null,
    pageTitle: String? = null,
    description: String? = null,
    enableNextButton: Boolean = true,
    enabledBackButton: Boolean = true,
    navController: NavHostController,
    prevNavigation: String = "forgot_password",
    nextButtonOnClick: (() -> Unit)? = null,
    nextNavigation: String = "forgot_password",
    content: @Composable () -> Unit ){
   Scaffold(
       topBar = {
           TopAppBar(
               title = {},
               navigationIcon = {
                   Spacer(Modifier.height(60.dp))
                   if(true){
                       Button(
                           onClick = {
                               navController.navigate(prevNavigation)
                           },
                           enabled = enabledBackButton,
                           colors = ButtonDefaults.buttonColors(
                               containerColor = Color(0xDD33CCEE),
                               contentColor = Color.White
                           ),
                           shape = RoundedCornerShape(percent = 20),
                           modifier = Modifier.size(68.dp).padding(top = 20.dp, start = 20.dp),
                           contentPadding = PaddingValues(0.dp)
                       ) {
                           Icon(
                               imageVector = Icons.Filled.KeyboardArrowLeft,
                               contentDescription = "Turn back",
                               modifier = Modifier.fillMaxSize(0.8f)
                           )
                       }
                   }
               }
           )
       }
   ) {
       innerPadding ->
       Column(
           modifier = Modifier.fillMaxSize()
               .padding(innerPadding),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Image(
               painter = painterResource(R.drawable.main_logo),
               contentDescription = "Main logo",
               modifier = Modifier.size(100.dp)
           )
           Text(
               text = appTitle ?: "Smart Task",
               fontSize = 24.sp,
               color = Color(0xFF55DDFF),
               fontWeight = FontWeight.Bold
           )
           Text(
               text = pageTitle ?: "Page Title",
               fontSize = 19.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.padding(top = 20.dp)
           )
           Text(
               text = description ?: "Just for Testing",
               fontSize = 14.sp,
               textAlign = TextAlign.Center,
               modifier = Modifier.padding(vertical = 10.dp)
           )
           // Additional Components here!
           content()

           Spacer(Modifier.height(30.dp))

           Button(
               onClick = {
                   nextButtonOnClick?.invoke()
                   navController.navigate(nextNavigation)},
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xDD33CCEE),
                   contentColor = Color.White
               ),
               modifier = Modifier.fillMaxWidth(0.7f),
               contentPadding = PaddingValues(2.dp),
               enabled = enableNextButton
           ){
               Text(text = "Next", fontSize = 20.sp)
           }

       }
   }
}


// Notification:
@Composable
fun TopBanner(message: String, show: Boolean, onDismiss: () -> Unit, durationMillis: Long = 3000L){
    if (show){
        LaunchedEffect(key1 = message) {
            delay(durationMillis)
            onDismiss()
        }
    }
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically(initialOffsetY = {-it}) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = {-it}) + fadeOut()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .background(color = Color(0xFF323232), shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ){
            Text(
                text = message,
                color = Color.White
            )
        }
    }
}