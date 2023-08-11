package com.siksinhot.test.server.retrofit

import com.siksinhot.test.server.model.PostBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class TwinkoreaService {
    var serverUrl: String
    var retrofit: Retrofit
    var service: TwinkoreaInterface
    constructor(serverUrl: String) {
        this.serverUrl = if (serverUrl.trim().endsWith("/"))  serverUrl.trim().substring(0, serverUrl.trim().length-1) else serverUrl.trim()
        this.retrofit = Retrofit.Builder()
            .baseUrl(this.serverUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(getOKHttp())
            .build()
        this.service  = retrofit.create(TwinkoreaInterface::class.java)
    }

    open fun getInstance(): TwinkoreaInterface {
        return this.service;
    }

    interface TwinkoreaInterface {
        @Headers(
            "Content-Type: application/json"
        )
        @POST("/async/siksin/produce")
        fun send(@Body body: PostBody): Call<PostBody>

    }
}