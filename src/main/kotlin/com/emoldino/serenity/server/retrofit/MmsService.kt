package com.emoldino.serenity.server.retrofit

import com.emoldino.serenity.server.model.PostBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Body
import rx.Observable

class MmsService {
    var serverUrl: String
    var retrofit: Retrofit
    var service: MmsInterface
    constructor(serverUrl: String) {
        this.serverUrl = serverUrl
        this.retrofit = Retrofit.Builder()
            .baseUrl(this.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.service  = retrofit.create(MmsInterface::class.java)
    }

    open fun getInstance(): MmsInterface {
        return this.service;
    }

    interface MmsInterface {
        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/interface/ai/fetchData")
        fun fetchData(@Body body: PostBody): Observable<PostBody>

        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/interface/ai/results")
        fun results(@Body body: PostBody): Observable<Any>
    }
}