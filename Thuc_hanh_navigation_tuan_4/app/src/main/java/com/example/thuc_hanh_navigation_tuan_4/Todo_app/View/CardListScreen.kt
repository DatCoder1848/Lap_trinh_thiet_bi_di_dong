package com.example.thuc_hanh_navigation_tuan_4.Todo_app.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.LineStyle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.ViewModel.CardViewModel
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.reorderable
import kotlin.collections.toMutableList


@Composable
fun CardListScreen(navController: NavHostController, viewModel: CardViewModel = viewModel()){
    val cards by viewModel.cards.collectAsState()
    val reorderableState = rememberReorderableLazyListState(
        onMove = { from, to ->
            viewModel.moveCard(from.index, to.index)
        }
    )
    val colorIndex: (Int) -> Int = { index ->
        if (index % 2 == 0) 0x55FDB000.toInt() else 0x55FE00B0.toInt()
    }

    CommonFrameLayout(
        title = "List",
        navController = navController,
        enableBottomBar = true,
        navigationIcon = { TurnBackButton(navController) }
    ) { innerPadding ->
        LazyColumn(
            state = reorderableState.listState,
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .reorderable(reorderableState)
                .background(color = Color(0x33E7FF33))
                .clip(RoundedCornerShape(percent = 10)),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(
                items = cards,
                key = { _, card -> card.id }
            ){ index, card ->
                ReorderableItem(reorderableState = reorderableState, key = card.id) { isDragging ->
                    Card(
                        modifier = Modifier.fillMaxWidth(0.9f)
                            .background(color = if (isDragging) Color.White else Color.LightGray)
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(colorIndex(index))
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = card.task,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                letterSpacing = 1.5.sp,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Text(
                                text = card.description,
                                fontSize = 19.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddCardScreen(navController: NavHostController, viewModel: CardViewModel = viewModel() ){
    var task by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }


    CommonFrameLayout(
        title = "Add new task",
        navigationIcon = { TurnBackButton(navController) },
        navController = navController,
        enableBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Spacer(Modifier.fillMaxHeight(0.1f))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "What Task?",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                OutlinedTextField(
                    value = task,
                    onValueChange = { task = it },
                    placeholder = { Text("What do you wanna do?")},
                    leadingIcon = {
                        Icon( imageVector = Icons.Filled.Edit, contentDescription = null, tint = Color.Black)
                    },
                    singleLine = true
                )

                Spacer(Modifier.fillMaxHeight(0.05f))

                Text(
                    text = "Tell more ...",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    placeholder = { Text("What or When or How do you wanna do?")}
                )
            }
            Button(
                onClick = {
                    // Save the task to the repository by using viewModel.addCard(task = ..., description = ...)
                    viewModel.addCard(task, desc)
                    navController.navigate("todo_list")
                },
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.Green
                ),
                modifier = Modifier.fillMaxWidth(0.9f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "Save your new Task",
                    fontSize = 20.sp
                )
            }
        }
    }
}


// My materials:
@Composable
fun TurnBackButton(navController: NavHostController){
    IconButton(onClick = {navController.navigateUp()}){
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
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
    navController: NavHostController? = null,
    enableBottomBar: Boolean = false,
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
        },
        bottomBar = {
            if (enableBottomBar){
                val currentRoute = navController?.currentBackStackEntryAsState()?.value?.destination?.route
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(56.dp)
                        .background(color = Color.White),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        onClick = { navController?.navigate("todo_list") }
                    ){
                        Icon(
                            imageVector = Icons.Filled.Layers,
                            contentDescription = "Todo list",
                            tint = if (currentRoute == "todo_list") Color(0xFF3388FF) else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = { navController?.navigate("add_task") }
                    ){
                        Icon(
                            imageVector = Icons.Filled.LibraryAdd,
                            contentDescription = "Add task",
                            tint = if (currentRoute == "add_task") Color(0xFF3388FF) else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = { }
                    ){
                        Icon(
                            imageVector = Icons.Filled.Checklist,
                            contentDescription = "",
                            tint = if (currentRoute == "") Color(0xFF3388FF) else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    IconButton(
                        onClick = { navController?.navigate("product_detail") }
                    ){
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = "",
                            tint = if (currentRoute == "product_detail") Color(0xFF3388FF) else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

