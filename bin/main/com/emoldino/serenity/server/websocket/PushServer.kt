@file:Suppress("NAME_SHADOWING")

package com.emoldino.serenity.server.websocket

import io.ktor.websocket.*
import io.ktor.server.websocket.WebSocketServerSession
import kotlinx.coroutines.*
import com.emoldino.serenity.exception.InvalidMessageException
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.env.clientIp
import com.emoldino.serenity.server.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.io.File
import java.io.FileOutputStream
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

private val logger = KotlinLogging.logger {}

/**
 * Class in charge of the logic of the push server.
 * It contains handlers to events and commands to send messages to specific users in the server.
 */
class PushServer<Any>() {
    //    var auth = SsoService()
    val random = Random(Date().time) // only for demo

    /**
     * A synchronizedSet map associating session IDs to user names.
     */
    val sessionMap = ConcurrentHashMap<Int, Session<Any>>()
    val channelMap = ConcurrentHashMap<String, ConcurrentHashMap<Int, Boolean>>()
    val sessionCountMap = ConcurrentHashMap<String, AtomicInteger>()

    fun connectionCounts(): ConnectionCounts {
        val list = ArrayList<SubscriptionCount>()
        for (entry in this.channelMap.entries) {
            val subscription = SubscriptionCount(entry.key, entry.value.size)
            list.add(subscription)
        }

        for (entry in Env.topicConsumeCounts) {
            val subscription = SubscriptionCount("[" + entry.key + " 데이타]", entry.value)
            list.add(subscription)
        }

        val connectionCounts = ConnectionCounts(sessionMap.size, list)

        return connectionCounts
    }

    /**
     * send [message] with Text Frame to a client [socket]
     */
    fun CoroutineScope.sendTo(socket: WebSocketServerSession, message: Message) {
        launch {
            try {
                if (socket.isActive) {
                    socket.outgoing.send(Frame.Text(Json.encodeToString(message)))
                } else {
                    close(socket, CloseReason.Codes.GOING_AWAY, "sendTo : a socket error")
                }
            } catch (e: Throwable) {
                close(socket, CloseReason.Codes.GOING_AWAY, "sendTo : a socket error")
            }
        }
    }

    /**
     * send [message] with Text Frame to client [socketList]
     */
    fun CoroutineScope.sendTo(socketList: List<WebSocketServerSession>, message: Message) {
        launch {
            socketList.send(Frame.Text(Json.encodeToString(message)));
        }
    }

    /**
     * send response ack [event] to a client [socket]
     */
    fun sendAck(socket: WebSocketServerSession, event: Event) {
        GlobalScope.launch {
            val message: Message =
                Message(null, Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC"))).time, event.value, Any())
            sendTo(socket, message)
        }
    }

    /**
     * send response error [event] to a client [socket]
     */
    fun sendError(socket: WebSocketServerSession, message: String?, errorMessage: String) {
        GlobalScope.launch {
            val error = Env.error()
            val errData: ChannelList = error.data as ChannelList
            if (message == null)
                errData.put("request_message", "Unknown")
            else
                errData.put("request_message", message)
            errData.put("err_description", errorMessage)

            sendTo(socket, error)
        }
    }

    /**
     * send response ack [event] to a client [socket]
     */
    fun sendTo(socket: WebSocketServerSession, message: Message) {
        GlobalScope.launch {
            sendTo(socket, message)
        }
    }

    /**
     * echo heartbeat [message] to the client user
     */
    suspend fun heartBeat(socket: WebSocketServerSession, message: Message) {
        // Checks if this user is already registered in the server and gives him/her a temporal name if required.

        GlobalScope.launch {
            sendTo(socket, message)
        }
    }

    fun onConnect(socket: WebSocketServerSession) {
        val ip = socket.call.clientIp

        var count = sessionCountMap.get(ip)
        if (count == null) {
            count = AtomicInteger()
            count.incrementAndGet()
            sessionCountMap.put(ip, count)
            @Suppress("UNCHECKED_CAST")
            sessionMap.put(socket.hashCode(), Session(socket, null as Any))
            sendAck(socket, Event.CONNECTED);

        } else {

            val current = count.get()
            if (current >= Env.connectionsLimit) {
                GlobalScope.launch {
                    close(
                        socket,
                        CloseReason.Codes.VIOLATED_POLICY,
                        "connect : same client's connections limit is over"
                    )
                }
            } else {
                count.incrementAndGet()
                sessionCountMap.put(ip, count)
                @Suppress("UNCHECKED_CAST")
                sessionMap.put(socket.hashCode(), Session(socket, null as Any))
                sendAck(socket, Event.CONNECTED);
            }

        }
    }

    /**
     * join client user [session] and [socket]
     */
    @Throws(Exception::class)
    suspend fun userJoin(socket: WebSocketServerSession, session: Any, message: Message) {
        // Checks if this user is already registered in the server and gives him/her a temporal name if required.

        if (session != null) {
            var channels: List<String>? = null

            //Subscription Channel Validation check
            try {
                @Suppress("UNCHECKED_CAST")
                val data = message.data as ChannelList

                @Suppress("UNCHECKED_CAST")
                channels = data.get("channels") as List<String>
            } catch (e: Throwable) {
                throw InvalidMessageException("subscription channels info is not valid")
            }


            for (channel in channels) {
                logger.debug { "userJoin : " + channel }
                val f1: List<String> = channel.split(":")
                if (f1.size == 1) {
                    val topic = channel.trim()

                    //Topioc 명 유효성 체크 [
                    var isTopic = false
                    Channel.values().forEach { v ->
                        run {
                            if (v.value == topic) {
                                isTopic = true
                                return@forEach
                            }
                        }
                    }

                    if (!isTopic) {
                        throw InvalidMessageException("unknown topic error : " + topic)
                    }
                    // ]

                    logger.debug { "subscribe key : " + topic }
                    var map = channelMap.get(topic)
                    if (map == null) {
                        map = ConcurrentHashMap<Int, Boolean>()
                    }
                    map.put(socket.hashCode(), true)
                    channelMap.put(topic, map)
                } else if (f1.size > 1) {
                    val topic = f1[0]

                    //Topioc 명 유효성 체크 [
                    var isTopic = false
                    Channel.values().forEach { v ->
                        run {
                            if (v.value == topic) {
                                isTopic = true
                                return@forEach
                            }
                        }
                    }

                    if (!isTopic) {
                        throw InvalidMessageException("unknown topic error : " + topic)
                    }
                    // ]

                    val currencyList = f1[1].split(",")
                    for (currency in currencyList) {
                        var key = topic + ":" + currency.trim()
                        logger.debug { "subscribe key : " + key }
                        var map = channelMap.get(key)
                        if (map == null) {
                            map = ConcurrentHashMap<Int, Boolean>()
                        }
                        map.put(socket.hashCode(), true)
                        channelMap.put(key, map)
                    }
                }
            }
            @Suppress("UNCHECKED_CAST")
            sessionMap.put(socket.hashCode(), Session(socket, message.data as Any))
            GlobalScope.launch {
                sendTo(socket, message)
            }
            logger.debug(Env.message("app.server.join"))
        } else {
            throw Exception("null session")
        }
    }

    /**
     * remove session and safe [socket] close with [code] and [message]
     */
    suspend fun close(socket: WebSocketServerSession?, code: CloseReason.Codes, message: String) {
        try {

            if (socket != null) {
                for (entry in channelMap.entries) {
                    entry.value.remove(socket.hashCode())
                    channelMap.put(entry.key, entry.value)
                }
                //ToDo : 세션 삭제시 return 값(user) 추후 필요한지 고려...
                sessionMap.remove(socket.hashCode())
                logger.debug(Env.message("app.server.close") + ":" + message + ":" + sessionMap.size)

                val ip = socket.call.clientIp

                var count = sessionCountMap.get(ip)
                if (count != null) {

                    val current = count.get()

                    count.decrementAndGet()
                    sessionCountMap.put(ip, count)
                }

                try {
                    socket.close(CloseReason(code, message))
                } catch (e: Throwable) {
                }
            }
        } catch (e: Throwable) {
        }
    }


    /**
     * Handles that a [user] with a specific [socket] left the server.
     */
    @Throws(Exception::class)
    suspend fun userLeft(socket: WebSocketServerSession, message: Message) {

        var channels: List<String>? = null
        try {
            @Suppress("UNCHECKED_CAST")
            val data = message.data as ChannelList

            @Suppress("UNCHECKED_CAST")
            channels = data.get("channels") as List<String>
        } catch (e: Throwable) {
            throw InvalidMessageException("unsubscription channels info is not valid")
        }

        for (channel in channels) {
            logger.debug { "userLeft : " + channels }
            val f1 = channel.split(":")
            if (f1.size == 1) {
                val topic = channel.trim()

                //Topioc 명 유효성 체크 [
                var isTopic = false
                Channel.values().forEach { v ->
                    run {
                        if (v.value == topic) {
                            isTopic = true
                            return@forEach
                        }
                    }
                }

                if (!isTopic) {
                    throw InvalidMessageException("unknown topic error : " + topic)
                }
                // ]

                val map = channelMap.get(topic)
                if (map != null) {
                    map.remove(socket.hashCode())
                    channelMap.put(channel, map)
                }
                channelMap.forEach { k, v ->
                    run {
                        if (k.startsWith(topic)) {
                            val map: ConcurrentHashMap<Int, Boolean>? = channelMap.get(k)
                            if (map != null) {
                                map.remove(socket.hashCode())
                                channelMap.put(k, map)
                            }
                        }
                    }
                }
            } else if (f1.size > 1) {
                val topic = f1[0]

                //Topioc 명 유효성 체크 [
                var isTopic = false
                Channel.values().forEach { v ->
                    run {
                        if (v.value == topic) {
                            isTopic = true
                            return@forEach
                        }
                    }
                }

                if (!isTopic) {
                    throw InvalidMessageException("unknown topic error : " + topic)
                }
                // ]

                val currencyList = f1[1].split(",")
                for (currency in currencyList) {
                    val key = topic + ":" + currency.trim()
                    val map = channelMap.get(key)
                    if (map != null) {
                        map.remove(socket.hashCode())
                        channelMap.put(key, map)
                    }
                }
            }
        }
        GlobalScope.launch {
            sendTo(socket, message)
        }
    }


    /**
     * Sends a [message] to all the users in the server, including all the connections per user.
     */
    fun broadcast(topic: String, message: Message) {
        logger.debug(Env.message("app.server.broadcastStart"))

        try {
            run {

                // 수신한 각 Topic 별 데이타 카운트 및 로깅 [
                synchronized(Env.topicLogging) {

                    val df = DateTimeFormatter.ofPattern("-yyyyMMdd")
                    val current = LocalDateTime.now(ZoneId.of("UTC"))
                    val dateStr = df.format(current)

                    val count: Int? = Env.topicConsumeCounts.get(topic + dateStr)
                    if (count == null) {
                        Env.topicConsumeCounts.put(topic + dateStr, 1)
                    } else {
                        Env.topicConsumeCounts.put(topic + dateStr, count + 1)
                    }

                    if (Env.topicLogging) {
                        if (!File("./log/").exists())
                            File("./log/").mkdir()

                        FileOutputStream(File("./log/" + topic + dateStr + ".log"), true).bufferedWriter()
                            .use { writer ->
                                writer.append(Json.encodeToString(message) + "\n")
                                writer.close()
                            }
                    }
                }
                // ]

                val map = ConcurrentHashMap<Int, Boolean>()
                var key = topic
                var tmp = this.channelMap.get(key)
                if (tmp != null) {
                    map.putAll(tmp)
                }

                when (topic) {
                    Channel.TICKER.value -> {
                        key = topic + ":" + (message.data as Ticker).currency_pair
                        tmp = this.channelMap.get(key)
                        if (tmp != null) {
                            map.putAll(tmp)
                        }
                    }
                    Channel.ORDERBOOK.value -> {
                        key = topic + ":" + (message.data as Orderbook).currency_pair
                        tmp = this.channelMap.get(key)
                        if (tmp != null) {
                            map.putAll(tmp)
                        }
                    }
                    Channel.TRANSACTION.value -> {
                        key = topic + ":" + (message.data as Transaction).currency_pair
                        tmp = this.channelMap.get(key)
                        if (tmp != null) {
                            map.putAll(tmp)
                        }
                    }
                }
                map.forEach { k, v ->
                    GlobalScope.launch {
                        val session = sessionMap.get(k)
                        if (session != null) {
                            sendTo(session.socketSession, message)
                        }
                    }
                }
            }
        } finally {
            logger.debug(Env.message("app.server.broadcast"), Json.encodeToString(message))
        }
    }


    /**
     * Sends a [message] to a list of [this] [WebSocketSession].
     */
    suspend fun List<WebSocketServerSession>.send(frame: Frame) {
        forEach {
            try {
                if (it.isActive) {
                    it.send(frame.copy())
                } else {
                    close(it, CloseReason.Codes.GOING_AWAY, "list.send() error...")
                }
            } catch (t: Throwable) {
                close(it, CloseReason.Codes.GOING_AWAY, "list.send() error...")
            }
        }
    }
}
