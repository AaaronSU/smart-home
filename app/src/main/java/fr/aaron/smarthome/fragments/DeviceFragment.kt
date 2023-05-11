package fr.aaron.smarthome.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.adapters.DeviceAdapter
import fr.aaron.smarthome.adapters.SensorAdapter
import fr.aaron.smarthome.repositories.DeviceRepository.Singleton.deviceList
import fr.aaron.smarthome.repositories.DeviceRepository.Singleton.devicedatabaseRef
import fr.aaron.smarthome.repositories.SensorRepository.Singleton.sensorList
import fr.aaron.smarthome.repositories.SensorRepository.Singleton.sensordatabaseRef

class DeviceFragment (
    private val context : MainActivity
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.devices_page, container, false)



        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.devices_recycle_view)


        val sensorDisplayListner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val recyclerView = view.findViewById<RecyclerView>(R.id.devices_recycle_view)
                Thread.sleep(1000)
                recyclerView.adapter = SensorAdapter(context, sensorList)
            }

            override fun onCancelled(error: DatabaseError) {
                // s'il ne trouve pas les elements en question
            }
        }


        val deviceDisplayListner = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val recyclerView = view.findViewById<RecyclerView>(R.id.devices_recycle_view)
                Thread.sleep(1000)
                recyclerView.adapter = DeviceAdapter(context, deviceList)
            }

            override fun onCancelled(error: DatabaseError) {
                // s'il ne trouve pas les elements en question
            }
        }



        verticalRecyclerView.adapter = DeviceAdapter(context, deviceList)



        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bar_devicesroom)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.sensor -> {
                    // Utiliser la liste de capteurs chargée précédemment
                    val recyclerView = view.findViewById<RecyclerView>(R.id.devices_recycle_view)
                    recyclerView.adapter = SensorAdapter(context, sensorList)

                    devicedatabaseRef.removeEventListener(deviceDisplayListner)
                    sensordatabaseRef.addValueEventListener(sensorDisplayListner)


                    return@setOnItemSelectedListener true
                }
                R.id.device -> {
                    val recyclerView = view.findViewById<RecyclerView>(R.id.devices_recycle_view)
                    recyclerView.adapter = DeviceAdapter(context, deviceList)

                    sensordatabaseRef.removeEventListener(sensorDisplayListner)
                    devicedatabaseRef.addValueEventListener(deviceDisplayListner)

                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
        return view
    }
}




