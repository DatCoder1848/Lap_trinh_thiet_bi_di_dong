package com.example.thuc_hanh_navigation_tuan_4.Todo_app.View

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.thuc_hanh_navigation_tuan_4.R
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.Model.Product
import com.example.thuc_hanh_navigation_tuan_4.Todo_app.ViewModel.ProductViewModel

@Composable
fun ProductScreen(navController: NavHostController, viewModel: ProductViewModel) {
    val item = Product(image = R.drawable.image_template_2, name = "Giày Sneaker Nam Nike Terra Manta - Trắng", price = 2749000, description = "GIÀY SNEAKER NAM NIKE TERRA MANTA\n" +
            "Giày Sneaker Nam Nike Terra Manta là sự kết hợp hoàn hảo giữa cảm hứng cổ điển và nét năng động đương thời. Với dáng cổ thấp mới mẻ, đôi giày tôn vinh quá khứ với phần upper làm từ vải dệt thoáng khí và lớp phủ da tổng hợp, mang lại cảm giác nhẹ chân, thoải mái suốt ngày dài. Đế ngoài dạng lugs lấy cảm hứng từ giày boots, giúp tăng độ bám và độ bền.\n" +
            "\n" +
            "THÔNG SỐ\n" +
            "Thân giày: Vải dệt kết hợp da tổng hợp – nhẹ, thoáng và ôm chân\n" +
            "Đế giữa: Lót foam êm ái, hỗ trợ giảm lực\n" +
            "Cổ giày: Đệm êm, tạo cảm giác dễ chịu quanh mắt cá\n" +
            "Đế ngoài: Cao su có đường may kiểu cupsole, đế gai phong cách boot – bám chắc, bền bỉ\n" +
            "Phong cách: Thấp cổ (low-profile), mang tinh thần retro hiện đại\n" +
            "Mã sản phẩm: HQ4502-101" )

    CommonFrameLayout(
        title = "Product detail",
        navigationIcon = { TurnBackButton(navController) },
        navController = navController,
        enableBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
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

fun showPrice(item: Product): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(item.price) + " VND"
}