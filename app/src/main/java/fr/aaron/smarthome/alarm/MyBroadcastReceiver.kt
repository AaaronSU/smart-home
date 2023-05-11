package fr.aaron.smarthome.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var i = Intent(context, AlarmOnActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(i)
    }
}