package com.example.thuc_hanh_tren_lop_tuan_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirportShuttle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thuc_hanh_tren_lop_tuan_3.ui.theme.Thuc_hanh_tren_lop_tuan_3Theme


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Thuc_hanh_tren_lop_tuan_3Theme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    var navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main" ){
        composable("main") { MainScreen(navController) }
        // Display group:
        composable("Text") { TextScreen(navController) }
        composable("Icon") { IconScreen(navController) }
        composable("Image") { ImageScreen(navController) }
        composable("Canvas") { CanvasScreen(navController) }
        // Input group:
        composable("TextField") { TextFieldScreen(navController) }
        composable("OutlinedTextField") { OutlinedTextFieldScreen(navController) }
        composable("BasicTextField") { BasicTextFieldScreen(navController) }
        composable("PasswordField") { PasswordFieldScreen(navController) }
        composable("Clickable") { ClickableComponentsScreen(navController) }
        // Layout:

        // Material Design:

        // Animation:

        //
    }
}

// My materials:
@Composable
fun TurnBackButton(navController: NavHostController){
    IconButton(onClick = {navController.navigateUp()}){
        Icon(
            painter = painterResource(R.drawable.icon_turn_back),
            contentDescription = "Turn back",
            modifier = Modifier.size(20.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonFrameLayout (
    title: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = title,
                        fontSize = 24.sp,
                        color = Color(0xFF6BC5F1),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = navigationIcon ?: {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun ImageAndTitle(
    @DrawableRes imageRes: Int,
    imageModifier: Modifier = Modifier
        .padding(top = 20.dp, bottom = 10.dp)
        .fillMaxWidth()
        .aspectRatio(1.5f)
        .border(2.dp, Color(0xFF000000)),
    title: String? = null
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(imageRes),
            contentDescription = "Image and title",
            modifier = imageModifier
        )

        if (title != null){
            Text(
                text = title,
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

// Pages:
@Composable
fun MainScreen(navController: NavHostController){
    CommonFrameLayout(title = "UI Components List")
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.SignalCellularAlt,
                contentDescription = null
            )
            Text(
                text = "Display",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Start,

                )

            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }

            Button(
                onClick = { navController.navigate("Icon")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Icon",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays icon",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Image")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Image",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays Image",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Canvas")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Canvas",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays Canvas Demo",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }

            // Input Group:
            Text(
                text = "Input",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Start,

                )

            Button(
                onClick = { navController.navigate("TextField")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "TextField",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text field",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("OutlinedTextField")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "OutlinedTextField",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays outlined text field",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }

            Button(
                onClick = { navController.navigate("PasswordField")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "PasswordField",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays password field",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Clickable")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Clickable Components",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays Clickable Components Demo",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }

            // Layout:
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Layout",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp),
                textAlign = TextAlign.Start,

                )
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
            Button(
                onClick = { navController.navigate("Text")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x9F55BBFF),
                    contentColor = Color(0xFF000000)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Text",
                        textAlign = TextAlign.Start
                    )

                    Text(
                        text = "Displays text",
                        color = Color(0x8A000000),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}

@Composable
fun TextScreen (navController: NavHostController){
    CommonFrameLayout(
        title = "Text Details",
        navigationIcon = { TurnBackButton(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RectangleShape)
                .padding(padding),
            contentAlignment = Alignment.Center,

        ){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center)
            {
                Text(
                    textAlign = TextAlign.Start,
                    fontSize = 32.sp,
                    lineHeight = 45.sp,
                    text =
                        buildAnnotatedString {
                            append("The ")
                            withStyle( style = SpanStyle(textDecoration = TextDecoration.LineThrough )) {
                                append("quick")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFCC6633), fontSize = 39.sp)) {
                                append(" Brown ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append("\nfox ")
                            }
                            withStyle(style = SpanStyle(letterSpacing = 10.sp)){
                                append("jumps")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold, fontStyle = FontStyle.Italic)) {
                                append(" over \n")
                            }
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                append("the")
                            }
                            withStyle(style = SpanStyle(fontFamily = FontFamily.Cursive)){
                                append(" lazy ")
                            }
                            withStyle(style = SpanStyle()) {
                                append("dog.")
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun IconScreen (navController: NavHostController) {
    CommonFrameLayout(
        title = "Icon Component",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    IconButton(
                        onClick = { navController.navigate("main")},
                        modifier = Modifier
                            .padding(20.dp)
                            .size(72.dp)
                            .clip(RectangleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_home),
                            contentDescription = "Turn back home",
                            modifier = Modifier.size(60.dp)
                        )
                    }

                    IconButton(
                        onClick = { navController.navigate("Text")},
                        modifier = Modifier
                            .padding(20.dp)
                            .size(72.dp)
                            .background(Color.Transparent)
                            .clip(RectangleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_text),
                            contentDescription = "Turn to text screen",
                            modifier = Modifier.size(60.dp),
                            tint = Color.Unspecified
                        )
                    }

                    IconButton(
                        onClick = { navController.navigate("Image")},
                        modifier = Modifier
                            .padding(20.dp)
                            .size(72.dp)
                            .background(Color.Transparent)
                            .clip(RectangleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_image),
                            contentDescription = "Turn to text screen",
                            modifier = Modifier.size(60.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageScreen(navController: NavHostController){
    CommonFrameLayout(
        title = "Image Component",
        navigationIcon = { TurnBackButton(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ){
            ImageAndTitle(
                imageRes = R.drawable.image_template_1,
                title = "A currious Dogg said: \"Hey, you look so yumm\"."
            )

            ImageAndTitle(
                imageRes = R.drawable.image_template_2,
                title = "Do yuh dare me?"
            )
        }
    }
}

@Composable
fun CanvasScreen(navController: NavHostController){
    CommonFrameLayout(
        title = "Canvas Component",
        navigationIcon = {TurnBackButton(navController)}
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier.padding(bottom = 60.dp),
                text = "Canvas Drawing Demo",
                fontSize = 35.sp,
                color = Color(0xFF22CCCC),
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Cursive
            )

            Canvas(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(0.67f)
                    .background(Color(0x1FDFBDDC))
                    .border(2.dp, Color.Gray)
            ) {
                var x = size.width/2
                var y = size.height/2 - 100f
                drawCircle(
                    color = Color(0xFF00FFFF),
                    radius = 180f,
                    center = Offset(x, y - 300f),
                    alpha = 0.5f
                )

                x -= 200f
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Yellow, Color(0xFFFF2255)),
                        start = Offset(x, y),
                        end = Offset(x+400f, y+360f)
                    ),
                    topLeft = Offset(x, y),
                    size = Size(400f, 360f),
                )

                y += 500f
                drawLine(
                    color = Color.Cyan,
                    start = Offset(50f, y),
                    end = Offset(size.width - 50f, y),
                    strokeWidth = 15f
                )

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "☻ This is drawText in Canvas ☺ ",
                        80f,
                        size.height - 100f,
                        android.graphics.Paint().apply{
                            color = android.graphics.Color.BLACK
                            textSize = 55f
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun TextFieldScreen(navController: NavHostController){

    var text by remember { mutableStateOf("") }

    CommonFrameLayout(
        title = "TextField Component",
        navigationIcon = { TurnBackButton(navController) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(
                "TextField Demo: ",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column( modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Regular TextField") },
                    placeholder = { Text(text = "Input text here", fontSize = 15.sp, color = Color.Yellow) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun OutlinedTextFieldScreen(navController: NavHostController){
    var outlinedText by remember { mutableStateOf("")}

    CommonFrameLayout(
        title = "Outlined TextField Component",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding -> 
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = outlinedText,
                onValueChange = { outlinedText = it },
                label = { Text(text = "Outlined Text Field", color = Color(0xFF3FDDCC))},
                placeholder = { 
                    Text(
                        text = "Input your content here !!",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        modifier = Modifier.height(35.dp)
                    )                },
                modifier = Modifier.fillMaxWidth().aspectRatio(6f)
            )
        }
    }
}

@Composable
fun BasicTextFieldScreen(navController: NavHostController){
    var basicText by remember { mutableStateOf("") }
    CommonFrameLayout(
        title = "Basic TextField Component",
        navigationIcon = { TurnBackButton(navController) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            BasicTextField(
                value = basicText,
                onValueChange = { basicText = it },
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .height(56.dp)
                    .border(2.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                    .padding(20.dp)
                    .background(Color(0xFFF5F5F5))
            )
        }
    }
}

@Composable
fun PasswordFieldScreen(navController: NavHostController){
    var password by remember { mutableStateOf("")}
    var passwordVisible by remember { mutableStateOf(false)}

    CommonFrameLayout(
        title = "Password Field Component",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password field Demo")},
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton( onClick = { passwordVisible = !passwordVisible} ){
                        val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        Icon(icon, contentDescription = null)
                    }
                }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ClickableComponentsScreen(navController: NavHostController){

    CommonFrameLayout(
        title = "Clickable Components",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            // Check box container:
            Column (modifier = Modifier.fillMaxWidth()){
                Text(
                    "Check Box Demo ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)

                var childCheckedStates = remember { mutableStateListOf(false, false, false) }
                var parentState = when {
                    childCheckedStates.all { it } -> ToggleableState.On
                    childCheckedStates.none { it } -> ToggleableState.Off
                    else -> ToggleableState.Indeterminate
                }

                Column {
                    Row( verticalAlignment = Alignment.CenterVertically ) {
                        Text("Select all ")
                        TriStateCheckbox(
                            state = parentState,
                            onClick = {
                                var newState = parentState != ToggleableState.On
                                childCheckedStates.forEachIndexed { index, _ ->
                                    childCheckedStates[index] = newState
                                }
                            }
                        )
                    }
                }
                childCheckedStates.forEachIndexed { index, checked ->
                    Column{
                        Row{
                            Text("Option ${index + 1} ")
                            Checkbox(
                                checked = checked,
                                onCheckedChange = { isChecked ->
                                    childCheckedStates[index] = isChecked
                                }
                            )
                        }
                    }
                }
                if (childCheckedStates.all { it }){
                    Text("All options selected ")
                }
            }

            // Radio button container:
            Column( modifier = Modifier.fillMaxWidth().selectableGroup() ){
                Text(
                    text = "Radio Button Demo",
                    fontSize =  26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                var radioOptions = listOf("Calls", "Missed", "Friends")
                var (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0])}
                radioOptions.forEach { text ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected =  text == selectedOption ,
                                onClick = { onOptionSelected(text)},
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ){
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            // Switch demo:
            Column(modifier = Modifier.fillMaxWidth()){
                Text("Switch Demo",
                    fontSize =  26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                var checked by remember { mutableStateOf(true)}
                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    thumbContent = if (checked) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize)
                            )
                        }
                    }else null,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            // Slider container:
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
            ){
                Text( text = "Slider Demo",
                    fontSize =  26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                var sliderPosition by remember { mutableStateOf(0f)}
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    thumb = {
                        Icon(
                            Icons.Filled.AirportShuttle,
                            contentDescription = null
                        )
                    }
                )
                Text( text = sliderPosition.toString() )

                // Advancement: Range Slider
                Text( text = "Slider Demo",
                    fontSize =  26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    textAlign = TextAlign.Center
                )
                var rangeSliderPosition by remember {mutableStateOf(0f..200f)}
                // Map start (0f..200f) to 80.dp..180.dp for height
                val imageHeight = (80f + (100f * (rangeSliderPosition.start / 200f))).coerceIn(80f, 180f)
                // Map endInclusive (0f..200f) to 80.dp..180.dp for width
                val imageWidth = (80f + (100f * (rangeSliderPosition.endInclusive / 200f))).coerceIn(80f, 180f)

                RangeSlider(
                    value = rangeSliderPosition,
                    onValueChange = { range -> rangeSliderPosition = range },
                    steps = 5,
                    valueRange = 0f..200f
                )
                Image(
                    painter = painterResource(R.drawable.image_template_2),
                    contentDescription = null,
                    modifier = Modifier.size(height = imageHeight.dp, width = imageWidth.dp)
                )
            }

        }
    }
}

@Composable
fun ColumnScreen(navController: NavHostController){
    CommonFrameLayout(
        title = "Column layout",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            // Simulation screen:
            Column(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .aspectRatio(0.45f)
                    .border(width = 5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(30.dp))

                for ( i in 1..25 ){
                    Card(
                        modifier = Modifier.fillMaxWidth(0.7f)
                            . aspectRatio(3f)
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(Color(0xAAEE88AA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        Text(
                            text = "Card " + i,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScreen(navController: NavHostController){
    CommonFrameLayout(
        title = "Row layout",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            // Simulation screen:
            Row(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .aspectRatio(0.45f)
                    .border(width = 5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for ( i in 1..25 ){
                    Card(
                        modifier = Modifier.fillMaxHeight(0.3f)
                            . aspectRatio(0.5f)
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(Color(0xAAEE88AA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        Text(
                            text = "Card " + i,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScreen(navController: NavHostController){
    CommonFrameLayout(
        title = "Box layout",
        navigationIcon = { TurnBackButton(navController)}
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            // Screen Frame:
            Column(modifier = Modifier.fillMaxWidth(0.7f)
                .aspectRatio(0.45f)
                .border(width = 5.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                // Camera:
                Box(
                    modifier = Modifier.size(20.dp)
                        .border(0.1.dp, Color(0x59000000), RoundedCornerShape(percent = 50))
                        .background(Color(0x59000022), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Box(
                        modifier = Modifier.size(12.dp)
                            .border(0.1.dp, Color(0xEE000000), RoundedCornerShape(percent = 50))
                            .background(Color(0xEE000022), shape = CircleShape)
                    )
                    {
                        Box(
                            modifier = Modifier.size(2.dp)
                                .offset(x = 2.dp, y = 2.dp)
                                .border(0.1.dp, Color(0x45FFFFFF), RoundedCornerShape(percent = 50))
                                .background(Color(0xEEFFFFFF), shape = CircleShape)
                        )
                    }
                }

                // Main container:
                var cardIndex by remember { mutableStateOf(1)}
                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ){
                    // Previous card:
                    Card(
                        modifier = Modifier.fillMaxHeight(0.5f)
                            .offset(x = -60.dp, y = -50.dp)
                            . aspectRatio(0.5f)
                            .padding(8.dp)
                            .zIndex(2f),
                        colors = CardDefaults.cardColors(Color(0xAAEE88AA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        var prevCardIndex = if(cardIndex > 1) cardIndex-1 else 5
                        Text(
                            text = "Card " + prevCardIndex,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    // Current diplayed card:
                    Card(
                        modifier = Modifier.fillMaxHeight(0.5f)
                            . aspectRatio(0.5f)
                            .padding(8.dp)
                            .zIndex(3f),
                        colors = CardDefaults.cardColors(Color(0xAAEE88AA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        var nextCardIndex = if (cardIndex < 5) cardIndex+1 else 1
                        Text(
                            text = "Card " + cardIndex,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    // Next card:
                    Card(
                        modifier = Modifier.fillMaxHeight(0.5f)
                            .offset(x = 60.dp, y = -50.dp)
                            . aspectRatio(0.5f)
                            .padding(8.dp)
                            .zIndex(2f),
                        colors = CardDefaults.cardColors(Color(0xAAEE88AA)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ){
                        var nextCardIndex = if (cardIndex < 5) cardIndex+1 else 1
                        Text(
                            text = "Card " + nextCardIndex,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()
                        .align(Alignment.BottomCenter)){
                        // Turn to previous card:
                        OutlinedButton(
                            onClick = { if(cardIndex > 1) cardIndex-- else cardIndex = 5},
                            modifier = Modifier.fillMaxWidth(0.5f).aspectRatio(1.3f),
                            shape = ButtonDefaults.outlinedShape,

                        ) {
                           Column(
                               Modifier.fillMaxSize(),
                               horizontalAlignment = Alignment.CenterHorizontally
                           ){
                               Icon(
                                   imageVector = Icons.Filled.DoubleArrow,
                                   contentDescription = null,
                                   modifier = Modifier.rotate(180f).padding(end = 8.dp)
                               )
                               Text(
                                   text = "Prev",
                                   fontSize = 20.sp
                               )
                           }
                        }
                        // Turn to next card:
                        OutlinedButton(
                            onClick = { if(cardIndex < 5) cardIndex++ else cardIndex = 1 },
                            modifier = Modifier.fillMaxWidth().aspectRatio(1.3f),
                            shape = ButtonDefaults.outlinedShape,

                            ) {
                            Column(
                                Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Icon(
                                    imageVector = Icons.Filled.DoubleArrow,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Next",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Preview : =================================================================================
@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    Test3()
}

//@Preview(showBackground = true)
@Composable
fun TextScreenPreview(){
    Test()
}

/*
//@Preview(showBackground = true)
@Composable
fun IconScreenPreview(){
    IconScreen(rememberNavController())
}

//@Preview(showBackground = true)
@Composable
fun ImageScreenPreview(){
    ImageScreen(rememberNavController())
}

//@Preview(showBackground = true)
@Composable
fun CanvasScreenPreview(){
    CanvasScreen(rememberNavController())
}

//@Preview(showBackground = false)
@Composable
fun TextFieldScreenPreview(){
    TextFieldScreen(rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun BasicTextFieldPreview(){
    OutlinedTextFieldScreen(rememberNavController())
}*/
