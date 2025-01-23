package com.example.uas_pam

import android.app.Application
import com.example.uas_pam.repository.AppContainer
import com.example.uas_pam.repository.FarmContainer

class FarmApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = FarmContainer()
    }
}