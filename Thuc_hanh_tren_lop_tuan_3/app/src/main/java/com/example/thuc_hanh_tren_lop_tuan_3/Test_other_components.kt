package com.example.thuc_hanh_tren_lop_tuan_3

import android.icu.text.DecimalFormat
import androidx.annotation.ColorInt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@Composable
fun Test(){
    var cards = List<Card> (size = 5, init = {index -> Card(task = "task: ${index}", desc = "desc: ${index}")})
    val colorIndex: (Int) -> Int = { index ->
        if (index % 2 == 0) 0x55FDB000.toInt() else 0x55FE00B0.toInt()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = cards,
            key = { _, card -> card.id }
        ) { index, card ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(color = Color(colorIndex(index)))
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(colorIndex(index))
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).background(color = Color(colorIndex(index)))
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
                        text = card.desc,
                        fontSize = 19.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

data class Card(
    val id: String = UUID.randomUUID().toString(),
    val task: String,
    val desc: String
)

@Composable
fun Test2(){
    var task by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(40.dp),
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

            },
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

object ProductIdGenerator {
    private var id = 0

    fun createId(): String {
        return "Product_${++id}"
    }
}

data class Product(
    val id: String = ProductIdGenerator.createId(),
    val image: Int,
    val name: String, val price: Long,
    val description: String
)

fun showPrice(item: Product): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(item.price) + " VND"
}

@Composable
fun Test3(){
    val item = Product(image = R.drawable.image_template_2,name = "Giày Sneaker Nam Nike Terra Manta - Trắng", price = 2749000, description = "GIÀY SNEAKER NAM NIKE TERRA MANTA\n" +
            "Giày Sneaker Nam Nike Terra Manta là sự kết hợp hoàn hảo giữa cảm hứng cổ điển và nét năng động đương thời. Với dáng cổ thấp mới mẻ, đôi giày tôn vinh quá khứ với phần upper làm từ vải dệt thoáng khí và lớp phủ da tổng hợp, mang lại cảm giác nhẹ chân, thoải mái suốt ngày dài. Đế ngoài dạng lugs lấy cảm hứng từ giày boots, giúp tăng độ bám và độ bền.\n" +
            "\n" +
            "THÔNG SỐ\n" +
            "Thân giày: Vải dệt kết hợp da tổng hợp – nhẹ, thoáng và ôm chân\n" +
            "Đế giữa: Lót foam êm ái, hỗ trợ giảm lực\n" +
            "Cổ giày: Đệm êm, tạo cảm giác dễ chịu quanh mắt cá\n" +
            "Đế ngoài: Cao su có đường may kiểu cupsole, đế gai phong cách boot – bám chắc, bền bỉ\n" +
            "Phong cách: Thấp cổ (low-profile), mang tinh thần retro hiện đại\n" +
            "Mã sản phẩm: HQ4502-101" )

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(20.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Image(
                painter = painterResource(item.image),
                contentDescription = "Product 1",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(color = Color.Unspecified, shape = RoundedCornerShape(percent = 20))
                    .clip(shape = RoundedCornerShape(percent = 20)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = item.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp
            )

            Text(
                text = showPrice(item),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFEE8833)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .background(color = Color(0x33555555), shape = RoundedCornerShape(percent = 8))
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = item.description,
                    fontSize = 15.sp
                )
            }

        }
    }
}

