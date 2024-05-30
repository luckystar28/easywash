package org.d3if3067.easywashfix.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3if3067.easywashfix.R
import org.d3if3067.easywashfix.navigation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EasyWashRegisterScreen(navController: NavHostController, viewModel: MainViewModel) {
    val fullname = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    fun registerUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Login.route)
                } else {
                    Toast.makeText(context, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCAE9FB)) // Add this line
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Center the image horizontally
        Box(
            modifier = Modifier
                .fillMaxWidth()
//                .wrapContentSize(align = Alignment.Center)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.mipmap.logo_fix),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(width = 500.dp, height = 250.dp)
                )
                Text(
                    text = "Selamat Datang di EasyWash!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = fullname.value,
            onValueChange = { newValue -> fullname.value = newValue },
            label = { Text("Masukan Nama Lengkap") },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email.value,
            onValueChange = { newValue -> email.value = newValue },
            label = { Text("Masukan Email Anda") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = phoneNumber.value,
            onValueChange = { newValue -> phoneNumber.value = newValue },
            label = { Text("Masukan Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password.value,
            onValueChange = { newValue -> password.value = newValue },
            label = { Text("Masukan password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = confirmPassword.value,
            onValueChange = { newValue -> confirmPassword.value = newValue },
            label = { Text("Ulangi password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (password.value == confirmPassword.value) {
                    viewModel.registerUser(
                        fullname.value,
                        email.value,
                        phoneNumber.value,
                        password.value,
                        onSuccess = {
                            navController.navigate(Screen.Dashboard.route)
                            Toast.makeText(context, "Sukses Terdaftar", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = {
                            Toast.makeText(context, "Gagal Daftar", Toast.LENGTH_SHORT).show()
                        }
                    )
                } else {
                    Toast.makeText(context, "Password anda salah", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF31E4EF), // Set button background color to hex #31E4EF
                contentColor = Color.White
            )
        ) {
            Text("Daftar")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Sudah memiliki akun? ",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            ClickableText(
                text = AnnotatedString("Masuk"),
                onClick = { navController.navigate(Screen.Login.route) }, // Navigate to register screen
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun EasyWashRegisterScreenPreview() {
    EasyWashRegisterScreen(rememberNavController(), viewModel = MainViewModel())
}
