package com.example.proyekakhirpsi.connect

import com.example.proyekakhirpsi.models.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    fun getRetrofit() : Retrofit{
        val token = ""
        val apikey = ""

        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("apikey" , apikey)
                .build()
            chain.proceed(newRequest)
        }).build()

        val rxAdapter = RxJava3CallAdapterFactory.create()
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://tyfwfpqltrmddstamjuv.supabase.co/rest/v1/")
            .addConverterFactory(
            GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build()

        return retrofit
    }

}
