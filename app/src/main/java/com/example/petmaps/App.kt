package com.example.petmaps

import android.app.Application
import android.content.Context
import com.example.petmaps.db.MarkersDatabase

class App : Application() {

    val database by lazy { MarkersDatabase.getDatabase(this) }
}

val Context.app: App get() = applicationContext as App