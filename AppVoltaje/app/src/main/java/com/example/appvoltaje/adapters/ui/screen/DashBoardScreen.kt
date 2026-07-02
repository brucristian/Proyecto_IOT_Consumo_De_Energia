package com.example.appvoltaje.adapters.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appvoltaje.adapters.ui.theme.*
import com.example.appvoltaje.adapters.ui.viewmodel.DashboardViewModel
import com.example.appvoltaje.domain.model.SensorData

private val DashboardBackground = Color(0xFFF7F9F4)

@Composable
fun DashboardScreen(viewModel: DashboardViewModel) {
    val sensorData by viewModel.sensorState.collectAsState()
    DashboardContent(sensorData = sensorData, userName = "Carlos García")
}

@Composable
fun DashboardContent(sensorData: SensorData?, userName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DashboardBackground)
    ) {
        // Encabezado verde
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGreen)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = "Buenos días,",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Contenido con esquinas redondeadas
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-16).dp)
                .background(
                    color = DashboardBackground,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {

            // Grid 2x2 de estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CompactStatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Bolt,
                    iconColor = AccentOrange,
                    title = "Voltaje",
                    value = sensorData?.voltage?.toString() ?: "--",
                    unit = "V"
                )
                CompactStatCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.DeviceThermostat,
                    iconColor = Color(0xFFE24B4A),
                    title = "Temperatura",
                    value = sensorData?.temperature?.toString() ?: "--",
                    unit = "°C"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            CompactStatCard(
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Default.DoorFront,
                iconColor = AccentBrown,
                title = "Estado puertas",
                value = if (sensorData?.magneticField == 0f) "Cerrado" else "Abierto",
                unit = ""
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Dispositivos recientes",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
        }
    }
}

@Composable
fun CompactStatCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    value: String,
    unit: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary,
                        modifier = Modifier.padding(start = 3.dp, bottom = 2.dp)
                    )
                }
            }
        }
    }
}