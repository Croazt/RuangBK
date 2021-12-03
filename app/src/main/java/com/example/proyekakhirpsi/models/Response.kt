package com.example.proyekakhirpsi.models
import android.provider.ContactsContract
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class Response (
    val code : Int,
    val message: String,
    val data : ContactsContract.Data
)