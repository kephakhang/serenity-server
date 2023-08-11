package com.siksinhot.test.response


class PageResponse(
    val last: Boolean,
    val totalPages: Long,
    val totalElement: Long,
    val size: Long,
    val number: Long,
    val numberOfElements: Long,
    val first: Boolean,
    val empty: Boolean,
    val content: List<SiksinApiResponseBase>
)

open class SiksinApiResponseBase()
