
#include "max6675.h"
MAX6675 thermocouple(32, 25, 27);




void setup() {
  // put your setup code here, to run once:
  Serial.begin (115200);
  //thermocouple.begin();

}

void loop() {
  int status = thermocouple.readCelsius(); // 0 = OK, otros números = Error
  Serial.println(status);
 
  delay(10000);
}
