package com.example.proyekakhirpsi.config

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.proyekakhirpsi.config.StoreManager

class StoreManager(private val _context: Context) {
    private val editor: SharedPreferences.Editor
    var pref: SharedPreferences
    private val PRIVATE_MODE = 0
    fun setData(email :String, password : String, image : String) {
        editor.putString("Email", email)
        Log.d("RuangBK", "email set")
        editor.putString("Password", password)
        Log.d("RuangBK", "password set")
        editor.putString("image", image)
        Log.d("RuangBK", "image set")
        editor.commit()
    }
    fun setEmail(email :String){
        editor.putString("Email", email)
        editor.commit()
    }
    val email: String?
        get() = pref.getString("Email", "")

    val password: String?
        get() = pref.getString("Password", "")

    val image: String?
        get() = pref.getString("image", "")

    companion object {
        private const val PREF_NAME = "_store"
    }

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
}