package com.siksinhot.test.exception


open class TwinException(val code: ErrorCode, cause: Throwable?=null, status: Int?=null, vararg args: String?) : Exception(cause){
  val httpStatus = status
  val argList = ArrayList<String>()

  init {
    if (args.size > 0) {
      for (x in args) argList.add(x as String)
    }
  }

}

class SessionNotFoundException(cause: Throwable?, vararg args: String?): TwinException(ErrorCode.E00003, cause, null, *args)
