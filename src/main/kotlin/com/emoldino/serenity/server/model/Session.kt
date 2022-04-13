package com.emoldino.serenity.server.model

import io.ktor.websocket.*

data class Session<Any>(val socketSession: WebSocketServerSession, val session: Any)
