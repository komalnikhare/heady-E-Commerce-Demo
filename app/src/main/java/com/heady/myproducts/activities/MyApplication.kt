package com.heady.myproducts.activities

import android.app.Application
import com.heady.myproducts.services.VolleyService

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        VolleyService.initialize(this)

    }
}