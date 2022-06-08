package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.model.PostBody
import mu.KotlinLogging
import rx.internal.util.ActionSubscriber

private val logger = KotlinLogging.logger {}

class EmolSubscriber {
    companion object {
        fun suscriber(url: String): ActionSubscriber<Any> {
            return ActionSubscriber<Any>(
                { it -> // onNext
                    if (it != null) {
                        logger.debug("${url} : SEND OK : ${Env.gson.toJson(it)}")
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

        fun redirectSuscriber(url: String, redirect: (body: PostBody) -> Unit): ActionSubscriber<PostBody> {
            return ActionSubscriber<PostBody>(
                { it -> // onNext
                    if (it != null) {
                        logger.debug("${url} : SEND OK : ${Env.gson.toJson(it)}")
                        redirect(it)
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
    }
}
