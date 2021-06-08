package com.example.englishwordproject.broad_and_prefs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.englishwordproject.MainActivity

class Mybroadcast:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action.equals(Intent.ACTION_SCREEN_OFF)){
            val i = Intent(context, MainActivity:: class.java)
            //MainActivity를 띄운다.
            context?.startActivity(i)
        }
    }
}