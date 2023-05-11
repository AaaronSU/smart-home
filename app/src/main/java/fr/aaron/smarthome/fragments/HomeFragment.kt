package fr.aaron.smarthome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.repositories.RoomRepository.Singleton.map
import fr.aaron.smarthome.repositories.RoomRepository.Singleton.roomList
import fr.aaron.smarthome.adapters.RoomHorizontalAdapter
import fr.aaron.smarthome.adapters.RoomVerticalAdapter

class HomeFragment(
    private val context: MainActivity
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.home_page, container, false)

        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
        verticalRecyclerView.adapter = RoomVerticalAdapter(context, roomList, map)

        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.room_name_bar)
        horizontalRecyclerView.adapter = RoomHorizontalAdapter(context, roomList, horizontalRecyclerView, verticalRecyclerView, map)

        return view
    }
}