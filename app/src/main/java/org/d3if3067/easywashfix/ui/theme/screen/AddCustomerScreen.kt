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
import org.d3if3067.easywashfix.model.Pelanggan
import org.d3if3067.easywashfix.navigation.Screen
import java.util.UUID

@Composable
fun AddCustomerScreen(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var nama by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

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
            color = Color.Black,  // Set text color to black
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
                text = "Tambah Data Pelanggan",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,  // Set text color to black
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Nama", color = Color.Black) },  // Set label text color to black
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)  // Set input text color to black
        )

        OutlinedTextField(
            value = nomorTelepon,
            onValueChange = { nomorTelepon = it },
            label = { Text("Nomor Telepon", color = Color.Black) },  // Set label text color to black
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)  // Set input text color to black
        )

        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text("Alamat", color = Color.Black) },  // Set label text color to black
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            textStyle = TextStyle(color = Color.Black)  // Set input text color to black
        )


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                savePelanggan(
                    auth = auth,
                    nama = nama,
                    nomorTelepon = nomorTelepon,
                    alamat = alamat,
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(Screen.DataCustomer.route)
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF00BCD4)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "Lihat Pelanggan",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

fun savePelanggan(
    auth: FirebaseAuth,
    nama: String,
    nomorTelepon: String,
    alamat: String,
    context: Context,
    onComplete: (Boolean, String?) -> Unit
) {
    val userId = auth.currentUser?.uid
    if (userId != null) {
        val db = FirebaseFirestore.getInstance()

        val pelangganId = UUID.randomUUID().toString()

        val pelanggan = Pelanggan(
            id = pelangganId,
            nama = nama,
            nomorTelepon = nomorTelepon,
            alamat = alamat
        )

        db.collection("users").document(userId).collection("pelanggan") // Path penyimpanan
            .document(pelangganId)
            .set(pelanggan)
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
fun AddCustomerScreenPreview() {
    val navController = rememberNavController()
    val viewModel = MainViewModel()
    val auth = FirebaseAuth.getInstance() // Assuming FirebaseAuth is initialized
    AddCustomerScreen(auth = auth, navController = navController, viewModel = viewModel)
}
