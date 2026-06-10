//TERMOCUPLA
#include "max6675.h"
MAX6675 thermocouple(32, 25, 27);

//SENSOR MAGNETICO DE CONTACTO
const int pinSensor = 26;
int estadoSensor = 0;

//VOLTAJE
#include "EmonLib.h"
EnergyMonitor emon;
#define vCalibration    109.2   // ✅ Ajustado para 110V red Colombia
#define currCalibration 55.5    // ✅ Ajustado para SCT-013-050 (50A)

float kWh = 0;
unsigned long lastmillis = 0;   // ✅ Inicializar en 0, no en millis()

void myTimerEvent() {
  emon.calcVI(20, 2000);

  unsigned long now = millis(); // ✅ Capturar tiempo UNA sola vez
  kWh += emon.apparentPower * (now - lastmillis) / 3600000000.0;
  lastmillis = now;             // ✅ Actualizar ANTES del delay, no después

  // yield() ← REMOVIDO, no tiene propósito aquí

  Serial.print("Vrms: ");
  Serial.print(emon.Vrms, 2);
  Serial.print("V");

  Serial.print("\tIrms: ");
  Serial.print(emon.Irms, 4);
  Serial.print("A");

  Serial.print("\tPower: ");
  Serial.print(emon.apparentPower, 4);
  Serial.print("W");

  Serial.print("\tkWh: ");
  Serial.print(kWh, 5);
  Serial.println("kWh");
}

void setup() {
  Serial.begin(115200);
  pinMode(pinSensor, INPUT_PULLUP);
  Serial.println("Iniciando prueba del sensor magnético...");

  emon.voltage(35, vCalibration, 1.7);
  emon.current(34, currCalibration);

  lastmillis = millis(); // ✅ Inicializar aquí, cuando el sistema ya arrancó
}

void loop() {
  // TERMOCUPLA
  int status = thermocouple.readCelsius();
  Serial.print("Temp: ");
  Serial.print(status);
  Serial.println("°C");

  // SENSOR MAGNETICO
  estadoSensor = digitalRead(pinSensor);
  if (estadoSensor == LOW) {
    Serial.println("Estado: CERRADO (0) -> Imán detectado");
  } else {
    Serial.println("Estado: ABIERTO (1) -> Sin imán");
  }

  // VOLTAJE
  myTimerEvent();

  Serial.println();
  Serial.println();

  delay(7000);
}