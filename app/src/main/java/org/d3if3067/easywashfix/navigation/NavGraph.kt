package org.d3if3067.easywashfix.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import org.d3if3067.easywashfix.ui.theme.screen.*

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    auth: FirebaseAuth
) {
    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) Screen.Dashboard.route else Screen.Login.route
    ) {
        composable(route = Screen.Register.route) {
            EasyWashRegisterScreen(navController, viewModel)
        }
        composable(route = Screen.Login.route) {
            EasyWashLoginScreen(navController, viewModel)
        }
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(navController, viewModel)
        }
        composable(route = Screen.TambahData.route) {
            AddDataScreen(navController = navController, viewModel = viewModel, auth = auth)
        }
        composable(route = Screen.DaftarHarga.route) {
            VehiclePriceListScreen(navController)
        }
        composable(route = Screen.AddCustomer.route) {
            AddCustomerScreen(navController = navController, auth = auth, viewModel = viewModel)
        }
        composable(route = Screen.DataKendaraan.route) {
            KendaraanScreen(auth = auth, navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.DaftarHargaMobil.route){
            CarCategoryScreen(navHostController = navController)
        }
        composable(route = Screen.DaftarHargaMotor.route){
           MotorPriceListScreen(navHostController = navController)
        }
        composable(route = Screen.LihatKendaraan.route){
            LihatKendaraanScreen(navController = navController, viewModel = viewModel, auth = auth)
        }
        composable(route = Screen.Laporan.route){
            ReportScreen(auth = auth, navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.DataCustomer.route) {
            CustomersScreen(navController = navController, viewModel = viewModel, auth = auth)
        }
    }
}
