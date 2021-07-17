package com.kalabukhov.app.freemoviesx.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class UsualService  : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Thread {

            stopSelf()
        }.start()
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

}