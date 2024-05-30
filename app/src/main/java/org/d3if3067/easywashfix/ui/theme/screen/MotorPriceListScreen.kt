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

data class MotorPrice(
    val imageRes: Int,
    val type: String,
    val category: String,
    val price: String
)

@Composable
fun MotorPriceListScreen(navHostController: NavHostController) {
    val motorPrices = listOf(
        MotorPrice(R.drawable.ic_motor1, "Kopling", "Besar", "25.000"),
        MotorPrice(R.drawable.ic_motor2, "Kopling", "Kecil", "20.000"),
        MotorPrice(R.drawable.ic_motor3, "Matic", "Kecil", "10.000"),
        MotorPrice(R.drawable.ic_motor4, "Matic", "Besar", "15.000"),
        MotorPrice(R.drawable.ic_motor5, "Manual", "Kecil", "10.000")
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
                text = "Motor",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Spacer di sini

        motorPrices.forEach { motorPrice ->
            MotorPriceCard(motorPrice = motorPrice, onClick = { /* handle edit action */ })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MotorPriceCard(motorPrice: MotorPrice, onClick: () -> Unit) {
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
                painter = painterResource(id = motorPrice.imageRes),
                contentDescription = motorPrice.type,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Jenis Motor: ${motorPrice.type}", fontWeight = FontWeight.Bold)
                Text(text = "Kategori Motor: ${motorPrice.category}")
                Text(text = "Harga: ${motorPrice.price}")
            }
//            Spacer(modifier = Modifier.weight(1f))
//            Icon(
//                painter = painterResource(id = R.drawable.ic_edit), // Replace with your edit icon resource
//                contentDescription = "Edit",
//                modifier = Modifier.size(24.dp)
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MotorPriceListScreenPreview() {
    MotorPriceListScreen(rememberNavController())
}
