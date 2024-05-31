package org.d3if3067.easywashfix.navigation

sealed class Screen (val route: String){

    data object Login: Screen("loginScreen")
    data object Register:  Screen("registerScreen")
    data object Dashboard: Screen("dashboardScreen")
    data object TambahData: Screen ("tambahData")
    data object DataKendaraan: Screen("vehiclePriceList")
    data object DataCustomer: Screen("dataCustomer")
    data object AddCustomer: Screen("addCustomer")
    data object Laporan: Screen("laporanScreen")
    data object DaftarHarga: Screen("vehiclePriceListScreen")
    data object DaftarHargaMobil: Screen("carCategoryScreen")
    data object LihatKendaraan: Screen ("lihatKendaraanScreen")
    data object DaftarHargaMotor: Screen("motorPriceListScren")

    data object Status: Screen("statusScreen")
    data object EditStatus: Screen("editStatusScreen")
}