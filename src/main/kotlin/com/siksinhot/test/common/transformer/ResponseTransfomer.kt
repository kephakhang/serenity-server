package com.siksinhot.test.transformer

import com.siksinhot.test.exception.TwinException
import com.siksinhot.test.server.auth.AuthenticationException
import mu.KotlinLogging
import org.apache.http.HttpStatus


private val logger = KotlinLogging.logger {}
data class JSONResponse(val status: Int, val data: Any?, val message: String? = null)

object ResponseTransformer {
    fun successResponse(data: Any): JSONResponse {
        val status = HttpStatus.SC_OK
        return JSONResponse(status = status, data = data)
    }

    fun failResponse(message: String?): JSONResponse {
        return JSONResponse(status = HttpStatus.SC_INTERNAL_SERVER_ERROR, data = null, message = message)
    }

    fun failResponseWithDataAndMessage(message: String?, data: Any): JSONResponse {
        return JSONResponse(status = HttpStatus.SC_INTERNAL_SERVER_ERROR, data = data, message = message)
    }

    fun successResponseOnlyStatus(): JSONResponse {
        return JSONResponse(status = HttpStatus.SC_OK, data = null)
    }

    fun failResponseOnlyStatus(): JSONResponse {
        return JSONResponse(status = HttpStatus.SC_INTERNAL_SERVER_ERROR, data = null)
    }

    fun failResponseWithStatusAndMessage(message: String?, status: Int): JSONResponse {
        return JSONResponse(status = status, data = null, message = message)
    }
}