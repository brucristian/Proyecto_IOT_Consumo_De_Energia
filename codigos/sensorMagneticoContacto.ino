// Definimos el pin digital donde está conectado el sensor
const int pinSensor = 26; 
// Variable para almacenar el estado actual del sensor
int estadoSensor = 0; 

void setup() {
  // Iniciamos la comunicación serial a 9600 baudios
  Serial.begin(9600);
  
  // Configuramos el pin del sensor como entrada.
  // Usamos INPUT_PULLUP por seguridad, lo que asegura un estado ALTO (1)
  // cuando el imán no está cerca, evitando lecturas "fantasma".
  pinMode(pinSensor, INPUT_PULLUP); 
  
  Serial.println("Iniciando prueba del sensor magnético...");
}

void loop() {
  // Leemos el estado digital del pin (0 o 1)
  estadoSensor = digitalRead(pinSensor);
  
  // Evaluamos el estado e imprimimos el resultado
  if (estadoSensor == LOW) {
    // Si lee LOW (0), el circuito se cerró a GND. ¡El imán está cerca!
    Serial.println("Estado: CERRADO (0) -> Imán detectado");
  } else {
    // Si lee HIGH (1), el circuito está abierto.
    Serial.println("Estado: ABIERTO (1) -> Sin imán");
  }
  
  // Una pequeña pausa de medio segundo para que puedas leer bien el monitor
  delay(500);
}
