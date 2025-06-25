package com.example.thuc_hanh_navigation_tuan_4


import android.media.Image
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UTurnLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.View.AddCardScreen
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.View.CardListScreen
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.View.ProductScreen
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.ViewModel.CardViewModel
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.ViewModel.ProductViewModel
import com.example.thuc_hanh_navigation_tuan_4.ui.theme.Thuc_hanh_navigation_tuan_4Theme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Thuc_hanh_navigation_tuan_4Theme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(){
    var navController = rememberNavController()
    val cardViewModel: CardViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()
    NavHost(navController = navController, startDestination = "splash"){
        composable("splash") {SplashScreen(navController)}
        composable("first") { FirstIntroScreen(navController) }
        composable("second") { SecondIntroScreen(navController) }
        composable("third") { ThirdIntroScreen(navController) }
        composable("todo_list") { CardListScreen(navController, cardViewModel)}
        composable("add_task") { AddCardScreen(navController, cardViewModel) }
        composable("place_holder_1") {  }
        composable("place_holder_2") { }
        composable("product_detail") { ProductScreen(navController, productViewModel) }
    }
}

@Composable
fun SplashScreen(navController: NavHostController){
    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate("first"){
            popUpTo("splash"){ inclusive = true }
        }
    }

    IphoneFrame { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(4f / 9f),
                contentScale = ContentScale.Fit,
                painter = painterResource(R.drawable.main_logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier.offset(y = 70.dp),
                text = "uth_smartta",
                fontSize = 37.sp,
                color = Color(0xff006EE9),
                fontFamily = FontFamily(Font(R.font.righteous_regular))
            )
        }
    }
}

@Composable
fun FirstIntroScreen(navController: NavHostController){
    IphoneFrame { innerPadding ->
        IntroScreen(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            imageResource = R.drawable.first_intro_img,
            title = "Easy time management",
            description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first ",
            prevRoute = null,
            nextRoute = "second"
        )
    }
}

@Composable
fun SecondIntroScreen(navController: NavHostController){
    IphoneFrame { innerPadding ->
        IntroScreen(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            title = "Increase work effectiveness",
            imageResource = R.drawable.second_intro_img,
            description = "Time management and the determination of more important tasks will give your job statistics better and always improve",
            prevRoute = "first",
            nextRoute = "third"
        )
    }
}

@Composable
fun ThirdIntroScreen(navController: NavHostController){
    IphoneFrame { innerPadding ->
        IntroScreen(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            title = "Reminder notification",
            imageResource = R.drawable.third_intro_img,
            description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set",
            prevRoute = "second",
            nextRoute = "todo_list"
        )
    }
}

/////////////// Preview ///////////////

@Preview(showBackground = true)
@Composable
fun ShowDemo(){
    ThirdIntroScreen(rememberNavController())
}


////////////////////////////////////

////////// My materials: ///////////

// Iphone Frame:
@Composable
fun IphoneFrame(content: @Composable ( (PaddingValues) -> Unit)){
    Scaffold (
        modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
        topBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                // Clock
                Text(
                    text = "9:41",
                    textAlign = TextAlign.Left
                )
                Spacer(Modifier.fillMaxWidth(0.4f))
                // Cell-Wifi_batery icons:
                Row(modifier = Modifier.fillMaxWidth(0.25f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Icon(
                        painter = painterResource(R.drawable.cell_icon),
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(R.drawable.wifi_icon),
                        contentDescription = null
                    )
                    Icon(
                        painter = painterResource(R.drawable.battery_icon),
                        contentDescription = null
                    )
                }
            }
        }
    ){ padding ->
        content(padding)
    }
}

// Introduction Screen Frame:
@Composable
fun IntroScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    imageResource: Int = R.drawable.main_logo,
    title: String,
    description: String,
    prevRoute: String? ,
    nextRoute: String?
){
    var currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var currentPage = when (currentRoute){
        "first" -> 0
        "second" -> 1
        "third" -> 2
        else -> 0
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top navigation bar:
        Row(Modifier.fillMaxWidth().padding(top = 40.dp, end = 20.dp)){
            DotsIndicator(total = 3, selectedIndex = currentPage)

            Text(
                text = "Skip",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {}),
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF55BBFF),
                textAlign = TextAlign.Right
            )
        }
        // Image container:
        Column(modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(0.86f)
                    .offset(y = 25.dp),
                painter = painterResource(imageResource),
                contentDescription = null
            )
        }
        // Content container:
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(0.86f).padding(bottom = 10.dp),
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 3.sp,
                lineHeight = 35.sp
            )
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(0.9f),
                fontSize = 18.sp,
                letterSpacing = 1.5.sp,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )
            Spacer(Modifier.weight(1f))
            Row(Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)) {
                if (prevRoute != null){
                    androidx.compose.material3.Button(
                        onClick = { navController.navigate(prevRoute) },
                        modifier = Modifier.size(60.dp),
                        shape = CircleShape,
                        colors= ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3388EE),
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.UTurnLeft,
                            contentDescription = "Turn back",
                            modifier = Modifier
                                .fillMaxSize()
                                .rotate(90f),
                            tint = Color.White
                        )
                    }
                }
                Spacer(Modifier.width(20.dp))
                if (nextRoute != null){
                    androidx.compose.material3.Button(
                        onClick = { navController.navigate(nextRoute)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(percent = 50),
                        colors= ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3388EE),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Next",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 3.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DotsIndicator(total: Int, selectedIndex: Int){
    Row(Modifier.width(200.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (index == selectedIndex) Color(0xFF55DDFF) else Color(0xFFAAAAAA) )
            )
        }
    }
}