package org.d3if3067.easywashfix.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.d3if3067.easywashfix.model.Kendaraan

@Composable
fun LihatKendaraanScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    auth: FirebaseAuth
) {
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    val customers = remember { mutableStateListOf<Vehicle>() }

    LaunchedEffect(Unit) {
        if (userId!= null) {
            db.collection("users").document(userId).collection("kendaraan")
                .get()
                .addOnSuccessListener { result ->
                    result.documents.forEach { document ->
                        val kendaraan = document.toObject(Kendaraan::class.java)
                        customers.add(Vehicle(kendaraan!!.nama, kendaraan.platNomor, kendaraan.jenisKendaraan))
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", e.message, e)
                }
        }
    }

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
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black  // Set icon tint color to black
                )
            }

            Text(
                text = "Data Kendaraan Pelanggan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,  // Set text color to black
                modifier = Modifier.padding(start = 8.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            customers.forEach { kendaraan ->
                CustomerCard(kendaraan)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CustomerCard(kendaraan: Vehicle) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama: ${kendaraan.nama}", fontWeight = FontWeight.Bold)
            Text(text = "Plat Nomor: ${kendaraan.platNomor}")
            Text(text = "Jenis Kendaraan: ${kendaraan.jenisKendaraan}")
        }
    }
}

data class Vehicle(
    val nama: String,
    val platNomor: String,
    val jenisKendaraan: String
)

@Preview
@Composable
fun LihatKendaraanScreenPreview() {
    val navController = rememberNavController()
    val viewModel: MainViewModel
    val auth = FirebaseAuth.getInstance() // Assuming FirebaseAuth is initialized
    LihatKendaraanScreen(navController = navController, viewModel = MainViewModel(), auth = auth)
}