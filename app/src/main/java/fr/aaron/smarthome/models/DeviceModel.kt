package fr.aaron.smarthome.models

data class DeviceModel(
    val deviceID: Int = 0,
    val name: String = "device",
    var turnOn : Boolean = false,
    val roomID: Int = 0
)
