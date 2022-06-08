package com.emoldino.serenity.server.retrofit

import com.emoldino.serenity.server.model.PostBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable

class DeepChainService {
    var serverUrl: String
    var retrofit: Retrofit
    var service: DeepChainInterface
    constructor(serverUrl: String) {
        this.serverUrl = if (serverUrl.trim().endsWith("/"))  serverUrl.trim().substring(0, serverUrl.trim().length-1) else serverUrl.trim()
        this.retrofit = Retrofit.Builder()
            .baseUrl(this.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(getOKHttp())
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
        @POST("/api/deepchain/launch")
        fun launch(@Body body: PostBody): Call<LinkedHashMap<String, Any>>

        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/deepchain/callback")
        fun callback(@Body body: PostBody): Call<LinkedHashMap<String, Any>>

        //========= return Observable ============//

        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/deepchain/launch")
        fun launch2(@Body body: PostBody): Observable<Response<LinkedHashMap<String, Any>>>

        @Headers(
            "Content-Type: application/json"
        )
        @POST("/api/deepchain/callback")
        fun callback2(@Body body: PostBody): Observable<Response<LinkedHashMap<String, Any>>>
    }
}