package fr.aaron.smarthome.alarm

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import fr.aaron.smarthome.R

class AlarmOnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_on)

        var mp = MediaPlayer.create(applicationContext, R.raw.alarm_on)
        mp.start()

        val button2 = findViewById<CardView>(R.id.turnOff)
        button2.setOnClickListener{
            mp.stop()
            finish()
        }
    }
}
