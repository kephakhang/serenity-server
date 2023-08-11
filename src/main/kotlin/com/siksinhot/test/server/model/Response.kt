package com.siksinhot.test.server.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class Response(
  open val body: Any?,
  val requestId: String?,
  val requestUri: String?,
  val method: String?
) {
}
