#include "EmonLib.h"
EnergyMonitor emon;

#define vCalibration    109.2  // ← Afinar con multímetro
#define currCalibration 55.5   // SCT-013-050 (50A)

float kWh = 0;
unsigned long lastMillis = 0;
const unsigned long INTERVAL_MS = 2500;

void setup() {
  Serial.begin(115200);
  emon.voltage(35, vCalibration, 1.7);  // pin 35, calibración, phase shift
  emon.current(34, currCalibration);    // pin 34, calibración
  lastMillis = millis();
}

void loop() {
  unsigned long now = millis();

  if (now - lastMillis >= INTERVAL_MS) {
    emon.calcVI(20, 2000);

    kWh += emon.apparentPower * (now - lastMillis) / 3600000000.0;
    lastMillis = now;

    Serial.print("Vrms: ");
    Serial.print(emon.Vrms, 2);
    Serial.print("V");

    Serial.print("\tIrms: ");
    Serial.print(emon.Irms, 4);
    Serial.print("A");

    Serial.print("\tPower: ");
    Serial.print(emon.apparentPower, 2);
    Serial.print("W");

    Serial.print("\tkWh: ");
    Serial.print(kWh, 5);
    Serial.println("kWh");
  }
}