package fr.aaron.smarthome.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.fragments.HomeDeviceFragment
import fr.aaron.smarthome.models.RoomModel
import fr.aaron.smarthome.repositories.DeviceRepository
import fr.aaron.smarthome.repositories.SensorRepository

class RoomVerticalAdapter(
    private val context: MainActivity,
    private val roomList: ArrayList<RoomModel>,
    private val map: Map<String, String>
) : RecyclerView.Adapter<RoomVerticalAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomName= view.findViewById<TextView>(R.id.home_page_room_name)
        val number_of_device = view.findViewById<TextView>(R.id.home_page_number_of_device)
        val number_of_sensor = view.findViewById<TextView>(R.id.home_page_number_of_sensor)
        val room_image = view.findViewById<ImageView>(R.id.room_image)
        val room_description_item = view.findViewById<CardView>(R.id.room_description_item)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomVerticalAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_vertical_room, parent, false)
        return RoomVerticalAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = roomList.size

    override fun onBindViewHolder(holder: RoomVerticalAdapter.ViewHolder, position: Int) {

        val currentRoom = roomList[position]

        // mettre à jour le nom de la chambre
        holder.roomName.text = currentRoom.roomName

        val sensorRepo = SensorRepository(context, null, null)
        val deviceRepo = DeviceRepository()

        // mettre à jour le nombre d'appareil
        holder.number_of_device.text = deviceRepo.number_of_device_in_a_room(currentRoom.roomId).size.toString() + " devices"

        // mettre à jour le nombre de capteurs
        holder.number_of_sensor.text = sensorRepo.number_of_sensor_in_a_room(currentRoom.roomId).size.toString() + " sensors"

        // utiliser glide pour récupérer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(map[currentRoom.roomCategory])).into(holder.room_image)

        holder.room_description_item.setOnClickListener{
            // val fragment = RoomFragment(currentRoom.roomId)
            val fragment = HomeDeviceFragment(context, currentRoom)
            val fragmentManager = context.supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.fragment_contrainer, fragment).commit()

        }
    }
}