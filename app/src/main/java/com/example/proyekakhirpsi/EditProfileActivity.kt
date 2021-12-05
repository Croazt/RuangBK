package com.example.proyekakhirpsi

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.proyekakhirpsi.config.StoreManager
import com.example.proyekakhirpsi.models.JanjiList
import kotlinx.android.synthetic.main.detail.*
import kotlinx.android.synthetic.main.detail.arrowback
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.android.synthetic.main.register.*
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity(){

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val passwordConfirmLiveData = MutableLiveData<String>()

    val PREF_NAME = "Shared"
    val NAMA_VAL = "Nama"
    val TANGGAL_VAL = "Tanggal"
    val GENDER_VAL = "Gender"
    val ALAMAT_VAL = "Alamat"
    lateinit var sharedPreference : SharedPreferences

     override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val storeManager = StoreManager(this)


            sharedPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            if (storeManager.email != null && storeManager.email!!.isNotEmpty()) {
                setContentView(R.layout.edit_profil)

                val storeManager = StoreManager(this)
                Glide.with(this)
                    .load(storeManager.image.toString())
                    .into(imgprofil)
                tvnamauser.text = storeManager.email.toString().split("@")[0]

                arrowback.setOnClickListener{
                    this.finish()
                }
                btndetail.setOnClickListener{
                    this.finish()
                }

            } else {
                Log.d("FACHRY", storeManager.email+"")
                startActivity(Intent(applicationContext, LoginActivity::class.java ))
                this.finish()
            }
        }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(NAMA_VAL, tvnamauser.text.toString())
        editor.putString(GENDER_VAL, tvgender.text.toString())
        editor.putString(TANGGAL_VAL, tvtgluser.text.toString())
        editor.putString(ALAMAT_VAL, tvlocuser.text.toString())
        editor.commit()
        editor.apply()
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        sharedPreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onRestoreInstanceState(savedInstanceState)

        val Nama = sharedPreference.getString(NAMA_VAL, "")
        val Tanggal = sharedPreference.getString(TANGGAL_VAL, "")
        val Gender = sharedPreference.getString(GENDER_VAL, "")
        val Alamat = sharedPreference.getString(ALAMAT_VAL, "")

        tvnamauser.text = Nama.toString()
        tvtgluser.text = Tanggal.toString()
        tvgender.text = Gender.toString()
        tvlocuser.text = Alamat.toString()
    }
}