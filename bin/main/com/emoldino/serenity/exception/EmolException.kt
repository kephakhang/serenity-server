package com.emoldino.serenity.exception

import org.eclipse.jetty.http.HttpStatus


open class EmolException(val code: ErrorCode, status: Int?=null, cause: Throwable?=null, vararg args: Any?) : Exception(cause){
  val httpStatus = HttpStatus.OK_200
  private val serialVersionUID = 1756502187423843041L

  val argList = ArrayList<Any>()

  init {
    for (x: Int in 1 .. args.size) argList.add(args.get(x) as Any)
  }

}

class SessionNotFoundException( cause: Throwable?, vararg args: Any?): EmolException(ErrorCode.E00003, null, cause, args) {
}
