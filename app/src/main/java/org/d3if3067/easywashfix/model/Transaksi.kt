package org.d3if3067.easywashfix.model

data class Transaksi(
    val id: String = "",
    val nama: String = "",
    val platNomor: String = "",
    val jenisKendaraan: String = "",
    val tanggal: String = "",
    val harga: Int = 0,
    val status: String = ""
)
