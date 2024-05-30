package org.d3if3067.easywashfix.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.roundToInt
import android.util.Log
import androidx.compose.ui.text.TextStyle
import org.d3if3067.easywashfix.model.Kendaraan

@Composable
fun AddDataScreen(navController: NavHostController, viewModel: MainViewModel, auth: FirebaseAuth) {
    val searchResults = remember { mutableStateListOf<String>() }
    val namaValue = remember { mutableStateOf("") }
    val platNomorValue = remember { mutableStateOf("") }

    fun performSearch(query: String, field: String) {
        val userId = auth.currentUser?.uid
        val db = FirebaseFirestore.getInstance()

        searchResults.clear()

        if (userId != null) {
            db.collection("users").document(userId).collection("kendaraan")
                .whereEqualTo(field, query)
                .get()
                .addOnSuccessListener { result ->
                    result.documents.forEach { document ->
                        val kendaraan = document.toObject(Kendaraan::class.java)
                        searchResults.add(kendaraan?.nama ?: "")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FirestoreError", e.message, e)
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCAE9FB))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

                Text(
                    text = "Transaksi",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Fields with search functionality
            InputField(label = "Nama:", value = namaValue.value, onValueChange = { namaValue.value = it }) {
                performSearch(it, "nama")
            }
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Plat Nomor:", value = platNomorValue.value, onValueChange = { platNomorValue.value = it }) {
                performSearch(it, "platNomor")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Rest of your UI code...
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit, onSearch: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = label, fontSize = 14.sp, color = Color.Gray)
                TextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                )
            }
            IconButton(
                onClick = { onSearch(value) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun AddDataScreenPreview() {
    val navController = rememberNavController()
    val viewModel = MainViewModel()
    val auth = FirebaseAuth.getInstance() // Assuming FirebaseAuth is initialized
    AddDataScreen(navController = navController, viewModel = viewModel, auth = auth)
}
