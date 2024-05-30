package org.d3if3067.easywashfix.ui.theme.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.d3if3067.easywashfix.model.Transaksi

import org.d3if3067.easywashfix.navigation.Screen
import java.util.UUID

@Composable
fun AddDataScreen(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var nama by remember { mutableStateOf("") }
    var platNomor by remember { mutableStateOf("") }
    var jenisKendaraan by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0F7FA))
            .padding(16.dp)
    ) {
        Text(
            text = "EasyWash",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                text = "Transaksi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )
        OutlinedTextField(
            value = tanggal,
            onValueChange = { tanggal = it },
            label = { Text("Tanggal", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = platNomor,
            onValueChange = { platNomor = it },
            label = { Text("Plat Nomor", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = jenisKendaraan,
            onValueChange = { jenisKendaraan = it },
            label = { Text("Jenis Kendaraan", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = harga,
            onValueChange = { harga = it },
            label = { Text("Harga", color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Radio button for status
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            RadioButton(
                selected = selectedStatus == "Dicuci",
                onClick = { selectedStatus = "Dicuci" },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
            Text(
                text = "Dicuci",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = selectedStatus == "Selesai",
                onClick = { selectedStatus = "Selesai" },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
            Text(
                text = "Selesai",
                color = Color.Black,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Button(
            onClick = {
                saveLaporan(
                    auth = auth,
                    nama = nama,
                    platNomor = platNomor,
                    jenisKendaraan = jenisKendaraan,
                    tanggal = tanggal,
                    harga = harga.toIntOrNull() ?: 0, // Convert to Int
                    status = selectedStatus,
                    context = context
                ) { success, errorMessage ->
                    if (success) {
                        navController.navigate(Screen.Dashboard.route)
                    } else {
                        errorMessage?.let { Log.e("FirestoreError", it) }
                    }
                }
            },
            shape = androidx.compose.foundation.shape.CircleShape,
            colors = ButtonDefaults.buttonColors(Color(0xFF00BCD4)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Tambah Data",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
    }
        fun saveLaporan(
    auth: FirebaseAuth,
    nama: String,
    platNomor: String,
    jenisKendaraan: String,
    tanggal: String,
    harga: Int,
    status: String,
    context: Context,
    onComplete: (Boolean, String?) -> Unit
) {
    val userId = auth.currentUser?.uid
    if (userId != null) {
        val db = FirebaseFirestore.getInstance()

        val transaksiId = UUID.randomUUID().toString()

        val transaksi = Transaksi(
            id = transaksiId,
            nama = nama,
            platNomor = platNomor,
            jenisKendaraan = jenisKendaraan,
            tanggal = tanggal,
            harga = harga,
            status = status
        )

        db.collection("users").document(userId).collection("transaksi")
            .document(transaksiId)
            .set(transaksi)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    } else {
        onComplete(false, "User not logged in")
    }
}


@Preview(showBackground = true)
@Composable
fun AddDataScreenPreview() {
    val navController = rememberNavController()
    val viewModel = MainViewModel()
    val auth = FirebaseAuth.getInstance() // Assuming FirebaseAuth is initialized
    AddDataScreen(auth = auth, navController = navController, viewModel = viewModel)
}
