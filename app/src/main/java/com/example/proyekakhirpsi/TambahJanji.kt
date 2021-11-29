package com.example.proyekakhirpsi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.databinding.ActivityMainBinding

class TambahJanji : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storeManager = StoreManager(this)


        if (storeManager.email != null && storeManager.email!!.isNotEmpty()) {

            setContentView(R.layout.addjanji)
        } else {
            Log.d("FACHRY", storeManager.email+"")
            startActivity(Intent(applicationContext, LoginActivity::class.java ))
            this.finish()
        }
    }

}