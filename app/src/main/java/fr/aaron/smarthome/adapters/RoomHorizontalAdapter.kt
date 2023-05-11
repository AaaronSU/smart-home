package fr.aaron.smarthome.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.aaron.smarthome.MainActivity
import fr.aaron.smarthome.R
import fr.aaron.smarthome.models.RoomModel

class RoomHorizontalAdapter(
    private val context: MainActivity,
    private val roomList: ArrayList<RoomModel>,
    private val roomHorizontalRecyclerView: RecyclerView,
    private val roomVerticalRecyclerView: RecyclerView,
    private val map: MutableMap<String, String>
) : RecyclerView.Adapter<RoomHorizontalAdapter.ViewHolder>(){

    private val roomCategory : List<String> = getRoomCategoryList(roomList)

    private fun getRoomCategoryList(roomList: List<RoomModel>): List<String> {
        val categorySet = mutableSetOf<String>()
        for (room in roomList) {
            categorySet.add(room.roomCategory)
        }
        return categorySet.toList()
    }
    fun getCategoryRooms(roomList: List<RoomModel>, category: String): ArrayList<RoomModel> {
        val roomListReturn = arrayListOf<RoomModel>()
        for (room in roomList) {
            if (room.roomCategory == category) {
                roomListReturn.add(room)
            }
        }
        return roomListReturn
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val roomName= view.findViewById<TextView>(R.id.room_bar_room_name)
        val active_or_not = view.findViewById<View>(R.id.room_active_or_not)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_horizontal_room, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = roomCategory.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.roomName.text = "All Rooms"
            holder.roomName.setTextColor(ContextCompat.getColor(context, R.color.light_blue))
            holder.active_or_not.visibility = View.VISIBLE
            setRoomOnClickListener(holder, "All Rooms")
        } else {
            val currentRoom = roomCategory[position - 1]

            // mettre à jour le nom de la chambre
            holder.roomName.text = currentRoom
            setRoomOnClickListener(holder, roomCategory[position - 1])
        }
    }

    private fun setRoomOnClickListener(holder: ViewHolder, text:String) {
        holder.roomName.setOnClickListener {
            setAllItemsDeactive {
                holder.roomName.setTextColor(ContextCompat.getColor(context, R.color.light_blue))
                holder.active_or_not.visibility = View.VISIBLE
                if (holder.roomName.text == "All Rooms") {
                    val categoryRoomList = roomList
                    roomVerticalRecyclerView.adapter = RoomVerticalAdapter(context, categoryRoomList, map)
                } else {
                    val categoryRoomList = getCategoryRooms(roomList, text)
                    roomVerticalRecyclerView.adapter = RoomVerticalAdapter(context, categoryRoomList, map)
                }
            }
        }
    }

    fun setAllItemsDeactive(callback: () -> Unit) {
        for (i in 0 until roomHorizontalRecyclerView.childCount) {
            val itemView = roomHorizontalRecyclerView.getChildAt(i)
            // Changer la couleur de fond de l'élément
            itemView.findViewById<TextView>(R.id.room_bar_room_name).setTextColor(ContextCompat.getColor(itemView.context, R.color.medium_gray))
            itemView.findViewById<View>(R.id.room_active_or_not).visibility = View.INVISIBLE
        }
        callback()
    }
}

