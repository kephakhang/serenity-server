package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.model.PostBody
import mu.KotlinLogging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.internal.util.ActionSubscriber

private val logger = KotlinLogging.logger {}

class EmolSubscriber {
    companion object {
        fun suscriber(url: String): ActionSubscriber<Response<LinkedHashMap<String, Any>>> {
            return ActionSubscriber<Response<LinkedHashMap<String, Any>>>(
                { it -> // onNext
                    if (it !== null && it.body() !== null) {
                        logger.debug("${url} : SEND OK : ${Env.gson.toJson(it.body())}")
                    } else {
                        logger.error("${url} : SEND ERROR : response is NULL")
                    }

                },
                { error ->  // onError
                    logger.error("${url} ERROR : ${error.stackTraceString}")

                },
                { // onCompleted
                    logger.debug("${url} : SEND COMPLETED ")
                })
        }

        fun redirectSuscriber(url: String, redirect: (body: PostBody) -> Unit): ActionSubscriber<Response<PostBody>> {
            return ActionSubscriber<Response<PostBody>>(
                { it -> // onNext
                    if (it !== null && it.body() !== null) {
                        logger.debug("${url} : SEND OK : ${Env.gson.toJson(it.body())}")
                        redirect(it.body() as PostBody)
                    } else {
                        logger.error("${url} : SEND ERROR : response is NULL")
                    }

                },
                { error ->  // onError
                    logger.error("${url} ERROR : ${error.stackTraceString}")

                },
                { // onCompleted
                    logger.debug("${url} : SEND COMPLETED ")
                })
        }

        fun callback(): Callback<LinkedHashMap<String, Any>> {
            return object : Callback<LinkedHashMap<String, Any>> {

                override fun onResponse(call: Call<LinkedHashMap<String, Any>>, response: Response<LinkedHashMap<String, Any>>) {
                    if (response !== null && response.body() !== null) {
                        logger.debug("${call.request().url} : SEND ${call.request().method} OK : ${Env.gson.toJson(response.body())}")
                    } else {
                        logger.error("${call.request().url} : SEND ${call.request().method} ERROR : response is NULL")
                    }
                }

                override fun onFailure(call: Call<LinkedHashMap<String, Any>>, t: Throwable) {
                    logger.error("${call.request().url} : SEND ${call.request().method} ERROR : ${t.stackTraceString}")
                }
            }
        }

        fun redirectCallback(redirect: (body: PostBody) -> Unit): Callback<PostBody> {
            return object : Callback<PostBody> {

                override fun onResponse(call: Call<PostBody>, response: Response<PostBody>) {
                    if (response !== null && response.body() !== null) {
                        logger.debug("${call.request().url} : SEND ${call.request().method} OK : ${Env.gson.toJson(response.body())}")
                        redirect(response.body() as PostBody)
                    } else {
                        logger.error("${call.request().url} : SEND ${call.request().method} ERROR : response is NULL")
                    }
                }

                override fun onFailure(call: Call<PostBody>, t: Throwable) {
                    logger.error("${call.request().url} : SEND ${call.request().method} ERROR : ${t.stackTraceString}")
                }
            }
        }
    }
}
