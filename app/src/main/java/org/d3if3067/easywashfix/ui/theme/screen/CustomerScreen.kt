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
import org.d3if3067.easywashfix.model.Pelanggan

@Composable
fun CustomersScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    auth: FirebaseAuth
) {
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    val customers = remember { mutableStateListOf<Customer>() }

    LaunchedEffect(Unit) {
        if (userId!= null) {
            db.collection("users").document(userId).collection("pelanggan")
                .get()
                .addOnSuccessListener { result ->
                    result.documents.forEach { document ->
                        val pelanggan = document.toObject(Pelanggan::class.java)
                        customers.add(Customer(pelanggan!!.nama, pelanggan.nomorTelepon, pelanggan.alamat))
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
                text = "Pelanggan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,  // Set text color to black
                modifier = Modifier.padding(start = 8.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            customers.forEach { customer ->
                CustomerCard(customer)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun CustomerCard(customer: Customer) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nama: ${customer.nama}", fontWeight = FontWeight.Bold)
            Text(text = "Nomor: ${customer.nomorTelepon}")
            Text(text = "Alamat: ${customer.alamat}")
        }
    }
}

data class Customer(
    val nama: String,
    val nomorTelepon: String,
    val alamat: String
)

@Preview
@Composable
fun CustomersScreenPreview() {
    val navController = rememberNavController()
    val viewModel: MainViewModel
    val auth = FirebaseAuth.getInstance() // Assuming FirebaseAuth is initialized
    CustomersScreen(navController = navController, viewModel = MainViewModel(), auth = auth)
}