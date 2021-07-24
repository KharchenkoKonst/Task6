package com.example.task6.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {
    @GET("/posts/{id}")
    suspend fun getData(@Path(value = "id") id: Int): NetworkModel
}