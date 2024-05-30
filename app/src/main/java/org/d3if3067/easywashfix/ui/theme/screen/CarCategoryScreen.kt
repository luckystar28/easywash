package org.d3if3067.easywashfix.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import org.d3if3067.easywashfix.R

data class MobiPrice(
    val imageRes: Int,
    val type: String,
    val category: String,
    val price: String
)

@Composable
fun CarCategoryScreen(navHostController: NavHostController) {
    val mobilPrices = listOf(
        MobiPrice(R.drawable.ic_suv, "Matic/Kopling", "SUV , MPV , OFF ROAD, Pickup, Wagon", "75.000"),
        MobiPrice(R.drawable.ic_sedan, "Matic/Kopling", " Sedan, City Car, Hybrid, Elektrik", "50.000"),
        MobiPrice(R.drawable.ic_sport, "Matic/Kopling", "Sport Car, Convertible", "100.000"),
        MobiPrice(R.drawable.ic_super_car, "Matic/Kopling", "Super Car, Hyper Car ", "200.000")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navHostController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Mobil",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Spacer di sini

        mobilPrices.forEach { mobilPrice ->
            MotorPriceCard(mobilPrice = mobilPrice, onClick = { /* handle edit action */ })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MotorPriceCard(mobilPrice: MobiPrice, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
//        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = mobilPrice.imageRes),
                contentDescription = mobilPrice.type,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Jenis Mobil: ${mobilPrice.type}", fontWeight = FontWeight.Bold)
                Text(text = "Kategori Mobil: ${mobilPrice.category}")
                Text(text = "Harga: ${mobilPrice.price}")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarCategoryScreenPreview() {
    CarCategoryScreen(rememberNavController())
}
