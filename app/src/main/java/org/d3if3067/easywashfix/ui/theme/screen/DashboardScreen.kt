package org.d3if3067.easywashfix.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3067.easywashfix.R // Import your drawable resources
import org.d3if3067.easywashfix.navigation.Screen

@Composable
fun DashboardScreen(navController: NavHostController , viewModel: MainViewModel) {
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

            Text(
                text = "EasyWash",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            IconButton(onClick = { viewModel.logout(navController) }) {
                Icon(Icons.Outlined.ExitToApp, contentDescription = stringResource(R.string.logout), tint = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Sedang Di Proses", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Nama : Asep Susanto")
                    Text(text = "Nomor Telepon : 081909203596")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Proses : ")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Dicuci",
                            color = Color.White,
                            modifier = Modifier
                                .background(Color(0xFFFFAB91), shape = RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImageCard(
                    imagePainter = painterResource(R.drawable.ic_car),
                    text = "Data Kendaraan",
                    navController = navController,
                    destination = Screen.DataKendaraan.route
                )
                ImageCard(
                    imagePainter = painterResource(R.drawable.ic_user),
                    text = "Data Pelanggan",
                    navController = navController,
                    destination = Screen.AddCustomer.route
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImageCard(
                    imagePainter = painterResource(R.drawable.ic_price_list),
                    text = "Daftar Harga",
                    navController = navController,
                    destination = Screen.DaftarHarga.route
                )
                ImageCard(
                    imagePainter = painterResource(R.drawable.ic_report),
                    text = "Laporan",
                    navController = navController,
                    destination = Screen.Laporan.route
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            FloatingActionButton(
                onClick = { navController.navigate(Screen.TambahData.route) },
                shape = CircleShape,
                containerColor = Color(0xFF00BCD4),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_transkasi),
                    contentDescription = "Transaksi",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ImageCard(imagePainter: Painter, text: String, navController: NavHostController, destination: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(150.dp)
            .padding(8.dp)
            .clickable { navController.navigate(destination) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = imagePainter,
                contentDescription = text,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(rememberNavController(), viewModel = MainViewModel())
}
