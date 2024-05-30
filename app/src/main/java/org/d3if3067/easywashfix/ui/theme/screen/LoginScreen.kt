package org.d3if3067.easywashfix.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3if3067.easywashfix.R
import org.d3if3067.easywashfix.navigation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EasyWashLoginScreen(navController: NavHostController, viewModel: MainViewModel) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

//    fun loginUser(email: String, password: String) {
//        val auth: FirebaseAuth = FirebaseAuth.getInstance()
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
//                    navController.navigate(Screen.Dashboard.route)
//                } else {
//                    Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCAE9FB)) // Add this line
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Center the image horizontally
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.Center)
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
            value = email.value,
            onValueChange = { newValue -> email.value = newValue },
            label = { Text("Enter your Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password.value,
            onValueChange = { newValue -> password.value = newValue },
            label = { Text("Enter password") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.height(16.dp))
        val signIn: () -> Unit = {
            }
        Button(
            onClick = {
                viewModel.loginUser(email.value, password.value, {
                    navController.navigate(Screen.Dashboard.route)
                }, {
                    Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                })
//                loginUser(email.value, password.value)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF31E4EF), // Set button background color to hex #31E4EF
                contentColor = Color.White
            )
        ) {
            Text("Masuk")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Belum memiliki akun? ",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            ClickableText(
                text = AnnotatedString("Register"),
                onClick = { navController.navigate(Screen.Register.route) }, // Navigate to register screen
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun EasyWashLoginScreenPreview() {
    EasyWashLoginScreen(rememberNavController(), viewModel = MainViewModel())
}
