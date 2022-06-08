package com.emoldino.serenity.server.retrofit

import com.emoldino.serenity.extensions.stackTraceString
import mu.KotlinLogging
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

fun getOKHttp(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
    val cache = Cache(File("/tmp/tmpcache.2"), 100 * 1024 * 1024)
    val interceptor = Interceptor { chain ->
        var request: Request = chain.request()
        val originalResponse: Response = chain.proceed(request)
        val cacheControl: String? = originalResponse.header("Cache-Control")
        if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
            cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0")
        ) {
            logger.debug("INTERCEPT", "SAVE A CACHE")
            val cc: CacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .build()
            request = request.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", "public")
                .cacheControl(cc)
                .build()
            chain.proceed(request)
        } else {
            logger.debug("INTERCEPT", "ONLINE FETCH")
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .build()
        }
    }

    val onlineOfflineHandler = Interceptor { chain ->
        try {
            logger.debug("INTERCEPT", "TRY ONLINE")
            chain.proceed(chain.request())
        } catch (e: Exception) {
            logger.error("INTERCEPT", "FALLBACK TO CACHE : ${e.stackTraceString}")

            val cacheControl: CacheControl = CacheControl.Builder()
                .maxStale(1, TimeUnit.DAYS)
                .onlyIfCached() //Caching condition
                .build()

            val offlineRequest: Request = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build()
            chain.proceed(offlineRequest)
        }
    }

    val httpClient = OkHttpClient.Builder()
        .cache(cache)
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(logging)
        .addInterceptor(onlineOfflineHandler)
        .build()

    return httpClient
}