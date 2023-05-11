package fr.aaron.smarthome.repositories
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.alarm.AlarmOnActivity
import fr.aaron.smarthome.repositories.SensorRepository.Singleton.sensorList
import fr.aaron.smarthome.repositories.SensorRepository.Singleton.sensordatabaseRef
import fr.aaron.smarthome.models.SensorModel

class SensorRepository(
    private val context: MainActivity,
    private val alarmManager: AlarmManager?,
    private val pendingIntent: PendingIntent?
) {

    fun number_of_sensor_in_a_room(room_id: Int) : List<SensorModel>{
        return sensorList.filter { it.roomID == room_id }
    }

    object Singleton {
        // se connecter à la référence Actuateur (device)
        val sensordatabaseRef = FirebaseDatabase.getInstance().getReference("CAPTEURS")

        // créer une liste qui va contenir les actuateurs
        val sensorList = arrayListOf<SensorModel>()
    }

    fun updateData(){
        // absober les données qu'on aurai aboser de la databaseRef
        sensordatabaseRef.addValueEventListener(object: ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciencnces qui ont ete enregistre
                sensorList.clear()
                // recolter la liste
                for (ds in snapshot.children) {
                    // construire l'objet device
                    val sensor = ds.getValue(SensorModel::class.java)
                    // verification que le device a ete charger
                    if (sensor != null) {
                        // ajout de l'actuateur
                        sensorList.add(sensor)
                        if (sensor.sensorID == 3 && sensor.sensorValue.toBoolean()) {
                            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // s'il ne trouve pas les elements en question
            }
        })
    }

    fun updateSensor(sensor: SensorModel) = sensordatabaseRef.child("Capteur " + sensor.sensorID.toString()).setValue(sensor)
}