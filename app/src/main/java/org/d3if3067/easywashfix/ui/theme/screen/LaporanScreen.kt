package org.d3if3067.easywashfix.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.d3if3067.easywashfix.model.Transaksi

@Composable
fun ReportScreen(auth: FirebaseAuth, navController: NavHostController, viewModel: MainViewModel) {
    var transactions by remember { mutableStateOf<List<Transaksi>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch data from Firestore when this composable is first displayed
    LaunchedEffect(Unit) {
        fetchTransactionsFromFirestore(auth) { result ->
            result.fold(
                onSuccess = { transactions = it },
                onFailure = { errorMessage = it.message }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3E5FC)) // Background color
            .padding(16.dp)
    ) {
        Text(
            text = "EasyWash",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black, // Text color
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { /* Handle back button click */ }
            ) {
                Text(
                    text = "Laporan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black // Text color
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ReportTableHeader()
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Unknown error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            transactions.forEach { transaction ->
                ReportTableRow(
                    name = transaction.nama,
                    vehicleType = transaction.jenisKendaraan,
                    nominal = transaction.harga.toString(),
                    date = transaction.tanggal
                )
            }
        }
    }
}

// Function to fetch transactions from Firestore
suspend fun fetchTransactionsFromFirestore(
    auth: FirebaseAuth,
    onComplete: (Result<List<Transaksi>>) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid

    if (userId != null) {
        try {
            val result = db.collection("users").document(userId).collection("transaksi").get().await()
            val transactions = result.map { it.toObject(Transaksi::class.java) }
            onComplete(Result.success(transactions))
        } catch (e: Exception) {
            Log.e("ReportScreen", "Error fetching transactions", e)
            onComplete(Result.failure(e))
        }
    } else {
        Log.e("ReportScreen", "User ID is null")
        onComplete(Result.failure(Exception("User ID is null")))
    }
}

@Composable
fun ReportTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Nama",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Jenis Kendaraan",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Nominal",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = "Tanggal",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}

@Composable
fun ReportTableRow(name: String, vehicleType: String, nominal: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = vehicleType,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = nominal,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = date,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    val navController = rememberNavController()
    val viewModel = MainViewModel()
    val auth = FirebaseAuth.getInstance()
    ReportScreen(auth = auth, navController = navController, viewModel = viewModel)
}
