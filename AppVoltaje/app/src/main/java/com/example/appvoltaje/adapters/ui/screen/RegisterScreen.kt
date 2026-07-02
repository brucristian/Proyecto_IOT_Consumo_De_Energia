package com.example.appvoltaje.adapters.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appvoltaje.adapters.ui.theme.LimeGreen
import com.example.appvoltaje.adapters.ui.theme.PrimaryGreen
import com.example.appvoltaje.adapters.ui.theme.TextSecondary
import com.example.appvoltaje.adapters.ui.viewmodel.AuthViewModel

enum class UserRole(val label: String) {
    TECNICO("Técnico"),
    OPERADOR("Operador"),
    ADMIN("Admin")
}

private val RegisterBackground = Color(0xFFF7F9F4)
private val FieldBorder = Color(0xFFD6DED8)

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onAuthSuccess: () -> Unit
) {
    // Estado local para apellido y rol (no están en el ViewModel base)
    var lastName by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.TECNICO) }

    // Observar éxito de autenticación
    LaunchedEffect(viewModel.isAuthSuccess) {
        if (viewModel.isAuthSuccess) {
            viewModel.clearForm()
            onAuthSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RegisterBackground)
    ) {
        // Sección superior verde
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryGreen)
                .padding(top = 48.dp, bottom = 40.dp, start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        // Sección inferior clara con el formulario
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .offset(y = (-24).dp)
                .background(
                    color = RegisterBackground,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Avatar placeholder con borde punteado verde
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = LimeGreen,
                        shape = CircleShape
                    )
                    .background(Color(0xFFE8F5E9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nombre y Apellido en fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Nombre",
                        fontSize = 13.sp,
                        color = Color(0xFF3A4A3D),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = viewModel.name,
                        onValueChange = { viewModel.name = it },
                        placeholder = {
                            Text("Carlos", color = TextSecondary, fontSize = 14.sp)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LimeGreen,
                            unfocusedBorderColor = FieldBorder,
                            focusedTextColor = Color(0xFF1C1C1C),
                            unfocusedTextColor = Color(0xFF1C1C1C)
                        ),
                        singleLine = true
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Apellido",
                        fontSize = 13.sp,
                        color = Color(0xFF3A4A3D),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        placeholder = {
                            Text("García", color = TextSecondary, fontSize = 14.sp)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LimeGreen,
                            unfocusedBorderColor = FieldBorder,
                            focusedTextColor = Color(0xFF1C1C1C),
                            unfocusedTextColor = Color(0xFF1C1C1C)
                        ),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Correo electrónico
            Text(
                text = "Correo electrónico",
                fontSize = 13.sp,
                color = Color(0xFF3A4A3D),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                placeholder = {
                    Text("carlos@email.com", color = TextSecondary, fontSize = 14.sp)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = LimeGreen,
                    unfocusedBorderColor = FieldBorder,
                    focusedTextColor = Color(0xFF1C1C1C),
                    unfocusedTextColor = Color(0xFF1C1C1C)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            Text(
                text = "Contraseña",
                fontSize = 13.sp,
                color = Color(0xFF3A4A3D),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
            )
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                placeholder = {
                    Text("••••••••", color = TextSecondary, fontSize = 14.sp)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = LimeGreen,
                    unfocusedBorderColor = FieldBorder,
                    focusedTextColor = Color(0xFF1C1C1C),
                    unfocusedTextColor = Color(0xFF1C1C1C)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Rol de usuario
            Text(
                text = "Rol de usuario",
                fontSize = 13.sp,
                color = Color(0xFF3A4A3D),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UserRole.entries.forEach { role ->
                    val isSelected = selectedRole == role
                    Button(
                        onClick = { selectedRole = role },
                        modifier = Modifier.wrapContentWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) PrimaryGreen else Color(0xFFE0E0E0),
                            contentColor = if (isSelected) Color.White else Color(0xFF5A5A5A)
                        ),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(text = role.label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Error message
            viewModel.errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = Color(0xFFE53935),
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Crear cuenta
            Button(
                onClick = { viewModel.onRegisterClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LimeGreen),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Crear cuenta",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes cuenta? ",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
                TextButton(onClick = onNavigateToLogin) {
                    Text(
                        text = "Inicia sesión",
                        color = PrimaryGreen,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}