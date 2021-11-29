package com.example.proyekakhirpsi.models

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("User")
    fun fetchUserLogin(@Query("select") select : String,@Query("email") email : String, @Query("password") password : String): Call<List<UserList>>
    fun fetchUser(@Query("select") select : String,@Query("email") email : String): Call<List<UserList>>

    @FormUrlEncoded
    @POST("User")
    fun createUser(@Field("email") email : String, @Field("password") password : String ): Call<List<UserList>>
}