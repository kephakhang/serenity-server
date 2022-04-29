package com.emoldino.serenity.server.env

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*

// ref : https://community.cloudflare.com/t/block-traffic-that-doesnt-come-through-cloudflare-at-aws-load-balancer/96006
// ref : https://support.cloudflare.com/hc/en-us/articles/204899617-Authenticated-Origin-Pulls
val ApplicationCall.clientIp: String
  get(): String {
    for (head: String in arrayOf("CF-Connecting-IP", "X-Forwarded-For", "True-Client-IP", "X-ProxyUser-Ip")) {
      val value = this.request.header(head)
      if (value != null) {
        return value.split(',')[0]
      }
    }
    return this.request.origin.remoteHost
  }
