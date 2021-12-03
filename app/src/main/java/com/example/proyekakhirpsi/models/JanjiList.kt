package com.example.proyekakhirpsi.models
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import java.io.Serializable

data class JanjiList  (
    @SerializedName("id")
    @Expose
    val id : Int,
    @SerializedName("topik")
    @Expose
    val topik : String,
    @SerializedName("kesimpulan")
    @Expose
    val kesimpulan : String,
    @SerializedName("nama")
    @Expose
    val nama : String,
    @SerializedName("kelas")
    @Expose
    val kelas : String,
    @SerializedName("guru")
    @Expose
    val guru : String,
    @SerializedName("created_at")
    @Expose
    val created_at : String,
    @SerializedName("ended_at")
    @Expose
    val ended_at : String,
    @SerializedName("isDone")
    @Expose
    val berjalan : String
): Serializable