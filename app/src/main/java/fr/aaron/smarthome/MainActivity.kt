package fr.aaron.smarthome

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.aaron.smarthome.alarm.MyBroadcastReceiver
import fr.aaron.smarthome.fragments.DeviceFragment
import fr.aaron.smarthome.fragments.HomeFragment
import fr.aaron.smarthome.repositories.DeviceRepository
import fr.aaron.smarthome.repositories.RoomRepository
import fr.aaron.smarthome.repositories.SensorRepository


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val roomRepo = RoomRepository()


        // Importer le bottomnavigationview
        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)

        roomRepo.updateData {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_contrainer, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        val deviceRepo = DeviceRepository()
        val sensorRepo = SensorRepository(this, alarmManager, pendingIntent)


        navigationView.setOnNavigationItemReselectedListener{
            when (it.itemId) {
                R.id.home_page -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_contrainer, HomeFragment(this))
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                R.id.devices_page -> {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_contrainer, DeviceFragment(this))
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                else -> Unit
            }
        }


        deviceRepo.updateData()
        sensorRepo.updateData()
    }
}

