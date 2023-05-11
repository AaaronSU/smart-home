package fr.aaron.smarthome.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.models.SensorModel
import fr.aaron.smarthome.repositories.RoomRepository
import fr.aaron.smarthome.repositories.SensorRepository
import fr.aaron.smarthome.repositories.SensorRepository.Singleton.sensordatabaseRef

class SensorAdapter(
    private val context: MainActivity,
    private val sensorList : List<SensorModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Type de vues des capteurs
    private val VIEW_TYPE_ANALOG_SENSOR = 0
    private val VIEW_TYPE_DETECTOR_SENSOR = 1

    // On crée deux listes distinctes pour chaque type de capteur
    private val analogSensorList = sensorList.filter { it.sensorType == "analog" }
    private val detectorSensorList = sensorList.filter { it.sensorType == "detector" }
    private val roomRepo = RoomRepository()

    override fun getItemCount(): Int = analogSensorList.size + detectorSensorList.size

    // On définit les ViewHolder pour chaque type de capteur
    class AnalogSensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconUrl = view.findViewById<ImageView>(R.id.icon_analogsensor)
        val sensorValue = view.findViewById<TextView>(R.id.present_value)
        val sensorName = view.findViewById<TextView>(R.id.analogsensor_name)
        val roomName = view.findViewById<TextView>(R.id.analogsensor_room_name)
    }

    class DetectorSensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iconUrl = view.findViewById<ImageView>(R.id.icon_detectorsensor)
        val sensorName = view.findViewById<TextView>(R.id.detectorsensor_name)
        val roomName = view.findViewById<TextView>(R.id.detectorsensor_room_name)
        val turnOn = view.findViewById<CardView>(R.id.cardview_detectorsensor)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < analogSensorList.size) {
            VIEW_TYPE_ANALOG_SENSOR
        } else {
            VIEW_TYPE_DETECTOR_SENSOR
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ANALOG_SENSOR) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_room_analog_sensor, parent, false)
            AnalogSensorViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_room_detector_sensor, parent, false)

            DetectorSensorViewHolder(view)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // On récupère l'élément du sensorList correspondant à cette position
        val currentSensor = if (position < analogSensorList.size) {
            analogSensorList[position]
        } else {
            detectorSensorList[position - analogSensorList.size]
        }

        val repository = SensorRepository(context, null, null)
        // On met à jour les informations du ViewHolder en fonction du type de capteur
        if (holder.itemViewType == VIEW_TYPE_ANALOG_SENSOR) {
            holder as AnalogSensorViewHolder
            holder.sensorName.text = currentSensor.name
            holder.sensorValue.text = currentSensor.sensorValue

            holder.roomName.text = roomRepo.get_room_by_id(currentSensor.roomID).roomName
        } else {
            holder as DetectorSensorViewHolder
            holder.sensorName.text = currentSensor.name

            holder.roomName.text = roomRepo.get_room_by_id(currentSensor.roomID).roomName

            if (currentSensor.sensorID == 3 && currentSensor.sensorValue.toBoolean()) {
                holder.turnOn.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_blue))
            }
        }
        repository.updateSensor(currentSensor)
    }
}