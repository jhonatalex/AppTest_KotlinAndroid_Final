package com.example.apptest_kotlinandroid.Model.Remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiApp {

    @GET("search_by_date?query=android")
    suspend fun fetchAllPost(): Response<PostRemote>



}