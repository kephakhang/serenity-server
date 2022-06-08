package com.emoldino.serenity.server.retrofit

import com.emoldino.serenity.server.model.PostBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Body
import rx.Observable

class DeepChainService {
    val serverUrl: String
    var retrofit: Retrofit
    var service: DeepChainInterface
    constructor(serverUrl: String) {
        this.serverUrl = serverUrl
        this.retrofit = Retrofit.Builder()
            .baseUrl(this.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.service  = retrofit.create(DeepChainInterface::class.java)
    }

    open fun getInstance():  DeepChainInterface {
        return this.service;
    }

    interface DeepChainInterface {
        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/ai/launch")
        fun launch(@Body body: PostBody): Observable<Any>

        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/ai/callback")
        fun callback(@Body body: PostBody): Observable<Any>
    }
}