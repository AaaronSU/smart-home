package fr.aaron.smarthome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.models.DeviceModel
import fr.aaron.smarthome.repositories.DeviceRepository
import fr.aaron.smarthome.repositories.RoomRepository

class DeviceAdapter(
    private val context: MainActivity,
    private val deviceList: List<DeviceModel>
): RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    private val roomRepo = RoomRepository()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cardviewdevice = view.findViewById<CardView>(R.id.cardview_device_off)
        val deviceName = view.findViewById<TextView>(R.id.component_name)
        val turnOn = view.findViewById<ImageView>(R.id.button_off)
        val roomName = view.findViewById<TextView>(R.id.component_room_name)
    }

    override fun getItemCount(): Int = deviceList.size

    // méthode d'injecter notre Layout ( injecter le composant )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_room_devices, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val currentDevice = deviceList[position]
        val deviceRepo = DeviceRepository()

        holder.deviceName.text = currentDevice.name
        if (currentDevice.turnOn) {
            holder.turnOn.setImageResource(R.drawable.button_on)
            holder.cardviewdevice.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_blue))

            holder.roomName.text = roomRepo.get_room_by_id(currentDevice.roomID).roomName
        } else {
            holder.turnOn.setImageResource(R.drawable.button_off)
            holder.cardviewdevice.setCardBackgroundColor(ContextCompat.getColor(context, R.color.dark_blue))

            holder.roomName.text = roomRepo.get_room_by_id(currentDevice.roomID).roomName
        }

        // rajouter une interaction
        holder.turnOn.setOnClickListener{
            //inverser si on touche le bouton
            currentDevice.turnOn = !currentDevice.turnOn
            deviceRepo.updateDevice(currentDevice)
            // mettre à jour la liste avec le nouvel état de l'élément
            notifyItemChanged(position)
        }

    }
}