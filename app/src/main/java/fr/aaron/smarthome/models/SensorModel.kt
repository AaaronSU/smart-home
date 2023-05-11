package fr.aaron.smarthome.models

class SensorModel (
    val sensorID: Int= 0,
    val sensorType: String = "analog",
    val name: String="Temperature",
    val sensorValue: String= "20Â°C",
    val roomID: Int= 0
)