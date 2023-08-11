package com.siksinhot.test.area.dto

import java.math.BigDecimal

data class AreaSubDto(
    var id: Long,
    var areaId: Int,
    var areaTitle: String,
    var latitude: BigDecimal,
    var longitude: BigDecimal,
    var isForeign: String? = null,
    var mainAreaId: Long,
)
