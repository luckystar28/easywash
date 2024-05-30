package org.d3if3067.easywashfix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3if3067.easywashfix.navigation.SetupNavGraph
import org.d3if3067.easywashfix.ui.theme.EasyWashFixTheme
import org.d3if3067.easywashfix.ui.theme.screen.MainViewModel

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyWashFixTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val mainViewModel: MainViewModel = viewModel()
                    SetupNavGraph(navController = navController, viewModel = mainViewModel,auth=auth)
                }
            }
        }
    }
}

