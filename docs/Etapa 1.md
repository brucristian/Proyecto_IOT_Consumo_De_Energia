**Etapa 1: Diseño, Montaje y Pruebas de Conexión**

- 1.  **Esquema del Circuito**

**Descripción técnica:** El circuito utiliza un divisor de tensión con un offset de **1.65V** (mediante resistencias de 10k y un capacitor de 10uF). Esto es fundamental para que el ADC del ESP32 pueda procesar la onda senoidal de la corriente alterna, que tiene componentes negativos, desplazándola a un rango de voltaje positivo seguro.

**1.2. Montaje Físico en Protoboard**



**Detalles de la implementación:**

- **Microcontrolador:** Se ha fijado el ESP32 en la protoboard asegurando la alimentación vía USB para las pruebas iniciales.
- **Acondicionamiento de Señal:** Se implementó el arreglo de resistencias y el capacitor para estabilizar la lectura de los sensores analógicos.
- **Cableado:** Se utilizaron Jumpers para conectar las salidas del módulo ZMPT101B y el jack del sensor SCT-013 a los pines GPIO correspondientes del ESP32.

**1.3. Pruebas de Conexión y Hardware**

- **Continuidad:** Se verificó con multímetro que no existan cortos en las líneas de 220V AC y que el offset de DC sea correcto.
- **Enlace de Software:** Prueba exitosa de carga de código desde el IDE de Arduino y detección del dispositivo en el puerto COM.
