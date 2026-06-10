#include "BluetoothSerial.h"
#include "EmonLib.h"

#if !defined(CONFIG_BT_ENABLED) || !defined(CONFIG_BLUEDROID_ENABLED)
#error Bluetooth is not enabled!
#endif

#if !defined(CONFIG_BT_SPP_ENABLED)
#error Serial Port Profile not available!
#endif

BluetoothSerial SerialBT;
EnergyMonitor emon;

#define vCalibration    109.2
#define currCalibration 55.5

String DEVICE_NAME = "ESP32-BT-001";

float kWh = 0;
unsigned long lastMillis = 0;
const unsigned long INTERVAL_MS = 2500;

void setup() {
  Serial.begin(115200);
  SerialBT.begin(DEVICE_NAME);
  emon.voltage(35, vCalibration, 1.7);
  emon.current(34, currCalibration);
  lastMillis = millis();
  Serial.println("Bluetooth iniciado: " + DEVICE_NAME);
}

void loop() {
  unsigned long now = millis();

  if (now - lastMillis >= INTERVAL_MS) {
    emon.calcVI(20, 2000);

    kWh += emon.apparentPower * (now - lastMillis) / 3600000000.0;
    lastMillis = now;

    // Serial Monitor
    Serial.print("Vrms: ");   Serial.print(emon.Vrms, 2);         Serial.print("V");
    Serial.print("\tIrms: "); Serial.print(emon.Irms, 4);         Serial.print("A");
    Serial.print("\tPower: "); Serial.print(emon.apparentPower, 2); Serial.print("W");
    Serial.print("\tkWh: ");  Serial.print(kWh, 5);               Serial.println("kWh");

    // Bluetooth → App Android
    String msg = "V:" + String(emon.Vrms, 2)
               + ",I:" + String(emon.Irms, 4)
               + ",P:" + String(emon.apparentPower, 2)
               + ",K:" + String(kWh, 5);
    SerialBT.println(msg);
  }
}