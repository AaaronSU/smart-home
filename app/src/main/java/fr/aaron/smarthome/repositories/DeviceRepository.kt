package fr.aaron.smarthome.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.aaron.smarthome.models.DeviceModel
import fr.aaron.smarthome.repositories.DeviceRepository.Singleton.deviceList
import fr.aaron.smarthome.repositories.DeviceRepository.Singleton.devicedatabaseRef

class DeviceRepository {object Singleton {
    // se connecter à la référence Actuateur (device)
    val devicedatabaseRef = FirebaseDatabase.getInstance().getReference("ACTUATEURS")

    // créer une liste qui va contenir les actuateurs
    val deviceList = arrayListOf<DeviceModel>()
}

    fun updateData() {
        // absober les données qu'on aurai aboser de la databaseRef
        devicedatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les actuateurs
                deviceList.clear()
                // recolter la liste
                for (ds in snapshot.children) {
                    // construire l'objet device
                    val device = ds.getValue(DeviceModel::class.java)
                    // verification que le device a ete charger
                    if (device != null) {
                        // ajout de l'actuateur
                        deviceList.add(device)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // s'il ne trouve pas les elements en question
                TODO("Not yet implemented")
            }

        })
    }

    fun updateDevice(device: DeviceModel) = devicedatabaseRef.child("Actuateur " + device.deviceID.toString()).setValue(device)

    fun number_of_device_in_a_room(room_id: Int) : List<DeviceModel>{
        return deviceList.filter { it.roomID == room_id }
    }
}
