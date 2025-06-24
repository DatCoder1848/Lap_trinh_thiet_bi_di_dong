package com.example.bai_tap_ve_nha_data_flow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun ProfileScreen(navController: NavHostController/*viewModel: ResetPasswordViewModel? = viewModel() */){
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            Button(
                onClick = {
                    navController.navigate("confirm")
                },
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
            Text(
                text = "Profile",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF33DDEE),
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        // Avatar
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.4f)
                .border(width = 2.dp, color = Color(0x88222222), shape = CircleShape),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier.fillMaxSize(0.5f).aspectRatio(1f)
                    .background(color = Color(0x33555555), shape = CircleShape)
                    .clip(CircleShape),
            ){
                Image(
                    painter = painterResource(R.drawable.my_avatar),
                    contentDescription = "User avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            /*Text(
                text = viewModel?.username ?: "gOOD bOY",
                color = Color(0xFF66DDFF),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(vertical = 20.dp)
            )*/
        }
        Spacer(Modifier.fillMaxHeight(0.12f))
        // info:
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "Email",
                fontSize = 20.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "HelloHello@mail.com",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
            )
            Text(
                text = "Nick name",
                fontSize = 20.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "gOOd bOY",
                fontSize = 24.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
                    .clickable(onClick = {})
            )
        }
        // Button
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
            onClick = {
                navController.navigate("confirm")},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xDD33CCEE),
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth(0.8f),
            contentPadding = PaddingValues(vertical = 10.dp)){
            Text(text = "Back", fontSize = 30.sp)
        } }
    }
}