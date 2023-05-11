package fr.aaron.smarthome.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.aaron.smarthome.repositories.RoomRepository.Singleton.databaseRef
import fr.aaron.smarthome.repositories.RoomRepository.Singleton.roomList
import fr.aaron.smarthome.models.RoomModel

class RoomRepository {

    fun get_room_by_id(roomId: Int) : RoomModel {
        return roomList.filter{it.roomId == roomId}[0]
    }

    object Singleton {
        // se connecter à la référence room
        val databaseRef = FirebaseDatabase
            .getInstance("https://smart-home-7cc80-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Rooms")

        // créer une liste qui va contenir nos chambres
        // val roomList = arrayListOf<RoomModel>()
        var roomList :ArrayList<RoomModel> = arrayListOf()

        val map: MutableMap<String, String> = mutableMapOf(
            "Salle de bain" to "https://bit.ly/3nKqydo",
            "Toilette" to "https://bit.ly/3NH8USA",
            "Chambre" to "https://bit.ly/3plVYqW",
            "Salon" to "https://bit.ly/3B5tnck"
        )
    }

    fun updateData(callback: () -> Unit) {
        // absorber les données depuis la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes
                roomList.clear()
                // récolter la liste
                for (ds in snapshot.children) {
                    // construire un objet plante
                    val room = ds.getValue(RoomModel::class.java)

                    // vérifier que la plante n'est pas null
                    if (room != null) {
                        // ajouter la plante à notre liste
                        roomList.add(room)
                    }
                }

                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}