package com.example.apptest_kotlinandroid.Model.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiClient {



    companion object{
        private const val BASE_URL="http://hn.algolia.com/api/v1/"


        fun retrofitInstance(): ApiApp {
            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofitClient.create(ApiApp::class.java)
        }

    }



}