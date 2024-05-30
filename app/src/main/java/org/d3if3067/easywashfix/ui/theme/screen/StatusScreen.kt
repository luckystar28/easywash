package org.d3if3067.easywashfix.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.FirebaseFirestore
import org.d3if3067.easywashfix.model.Status

@Composable
fun StatusScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Dicuci") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tombol Arrow Back
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }

            // Judul layar
            Text(
                text = "Status",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Nomor Telepon", color = Color.Black) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Status pencucian",
            fontSize = 16.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = status == "Dicuci",
                onClick = { status = "Dicuci" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = Color.Gray
                )
            )
            Text(text = "Dicuci", color = Color.Black)

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = status == "Selesai",
                onClick = { status = "Selesai" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Black,
                    unselectedColor = Color.Gray
                )
            )
            Text(text = "Selesai", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { addStatusToDatabase(name, phoneNumber, status) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF80DEEA))
        ) {
            Text(text = "Tambah Data", color = Color.White)
        }
    }
}

private fun addStatusToDatabase(name: String, phoneNumber: String, status: String) {
    val statusData = Status(name, phoneNumber, status)
    val db = FirebaseFirestore.getInstance()
    db.collection("status")
        .add(statusData)
        .addOnSuccessListener { documentReference ->
            // Handle success
        }
        .addOnFailureListener { e ->
            // Handle failure
        }
}

@Preview(showBackground = true)
@Composable
fun StatusScreenPreview() {
    val navController = rememberNavController()
    StatusScreen(navController = navController)
}
