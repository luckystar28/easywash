import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ReportScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB3E5FC)) // Warna latar belakang
            .padding(16.dp)
    ) {
        Text(
            text = "EasyWash",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black, // Warna teks diubah menjadi hitam
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
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
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { /* Handle back button click */ }
            ) {
                Text(
                    text = "Laporan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black // Warna teks diubah menjadi hitam
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ReportTableHeader()
        ReportTableRow("Davi Pramudya - Motor Matic Besar", "15.000", "28-06-2024")
        ReportTableRow("Tangguh - Mobil Suv Matic", "75.000", "28-06-2024")
        ReportTableRow("Udins - Motor Kopling Besar", "20.000", "28-06-2024")
    }
}


@Composable
fun ReportTableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Nama - Jenis Kendaraan",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = "Nominal",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = "Tanggal",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}

@Composable
fun ReportTableRow(name: String, nominal: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = nominal,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = date,
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun ReportScreenPreview() {
    ReportScreen(rememberNavController())
}
