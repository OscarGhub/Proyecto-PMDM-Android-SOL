import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitask.R
import com.example.fruitask.data.local.database.MyViewModel
import com.example.fruitask.ui.components.NumeroWheel
import com.example.fruitask.ui.theme.VerdeBoton
import com.example.fruitask.ui.theme.VerdeFondo

@Composable
fun Pantalla3(modifier: Modifier = Modifier, viewModel: MyViewModel) {

    var horas by remember { mutableIntStateOf(0) }
    var minutos by remember { mutableIntStateOf(0) }
    var segundos by remember { mutableIntStateOf(0) }

    var tiempoRestante by remember { mutableLongStateOf(0L) }
    var temporizadorActivo by remember { mutableStateOf(false) }
    var temporizadorPausado by remember { mutableStateOf(false) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    fun iniciarTimer(segundosIniciales: Long) {
        timer?.cancel()
        timer = object : CountDownTimer(segundosIniciales * 1000L, 1000) {
            override fun onTick(ms: Long) {
                tiempoRestante = ms / 1000
            }

            override fun onFinish() {
                tiempoRestante = 0
                temporizadorActivo = false
                temporizadorPausado = false
            }
        }.start()
        temporizadorActivo = true
        temporizadorPausado = false
    }

    fun resetearTemporizador() {
        timer?.cancel()
        temporizadorActivo = false
        temporizadorPausado = false
        tiempoRestante = 0
        horas = 0
        minutos = 0
        segundos = 0
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.temporizadorletrero),
                    contentDescription = "Mascota",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Ruedas solo si el temporizador NO está activo ni pausado
            if (!temporizadorActivo && !temporizadorPausado) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    NumeroWheel(horas, 0..23, "Horas") { horas = it }
                    NumeroWheel(minutos, 0..59, "Minutos") { minutos = it }
                    NumeroWheel(segundos, 0..59, "Segundos") { segundos = it }
                }
            }

            val h = tiempoRestante / 3600
            val m = (tiempoRestante % 3600) / 60
            val s = tiempoRestante % 60

            // Cronómetro
            if (tiempoRestante > 0 || temporizadorActivo || temporizadorPausado) {
                Text(
                    text = "%02d:%02d:%02d".format(h, m, s),
                    fontSize = 64.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = VerdeBoton,
                    modifier = Modifier
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .background(VerdeFondo)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                )
            }

            // Botón principal
            Button(
                onClick = {
                    when {
                        !temporizadorActivo && !temporizadorPausado -> {
                            val total = horas * 3600 + minutos * 60 + segundos
                            if (total > 0) iniciarTimer(total.toLong())
                        }
                        temporizadorActivo -> {
                            timer?.cancel()
                            temporizadorActivo = false
                            temporizadorPausado = true
                        }
                        else -> iniciarTimer(tiempoRestante)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = VerdeBoton),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .shadow(6.dp, RoundedCornerShape(30.dp))
            ) {
                Text(
                    when {
                        temporizadorPausado -> "Reanudar"
                        temporizadorActivo -> "Pausar"
                        else -> "Iniciar"
                    },
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón cancelar
            if (temporizadorActivo || temporizadorPausado) {
                Button(
                    onClick = { resetearTemporizador() },
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeBoton),
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .shadow(6.dp, RoundedCornerShape(30.dp))
                ) {
                    Text("✕ Cancelar", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}