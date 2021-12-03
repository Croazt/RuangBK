package com.example.proyekakhirpsi.models

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.*
import java.util.*
interface ApiService {
    @GET("User")
    fun fetchUserLogin(@Query("select") select : String,@Query("email") email : String, @Query("password") password : String): Call<List<UserList>>
    @GET("User")
    fun fetchUser(@Query("select") select : String,@Query("email") email : String): Call<List<UserList>>

    @FormUrlEncoded
    @POST("User")
    fun createUser(@Field("email") email : String, @Field("password") password : String ): Call<Void>

    @GET("Janji")
    fun fetchJanji(@Query("select") select : String,@Query("email") email : String): Observable<List<JanjiList>>
    @GET("Janji")
    fun fetchJanjiTopic(@Query("select") select : String,@Query("email") email : String, @Query("topik") topic : String): Observable<List<JanjiList>>
    @GET("Janji")
    fun fetchJanjiBerjalan(@Query("select") select : String,@Query("email") email : String, @Query("topik") topic : String, @Query("isDone") berjalan : String): Observable<List<JanjiList>>
    @GET("Janji")
    fun fetchJanjiAll(@Query("select") select : String): Observable<List<Response>>

    @FormUrlEncoded
    @POST("Janji")
    fun createJanji(@Field("topik") topik : String, @Field("nama") nama : String, @Field("kelas") kelas : String): Call<Void>
}