package com.siksinhot.test.exception

import mu.KotlinLogging
import kotlin.collections.HashMap

val logger = KotlinLogging.logger {}

class TwinError(
    val code: String,
    val status: Int,
    var description: String,
    val message: HashMap<String, String>,
    val origin: String
)
