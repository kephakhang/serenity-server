package com.siksinhot.test.area.dto

import java.math.BigDecimal

data class AreaMainDto(
    var id: Long,
    var areaId: Int,
    var areaTitle: String,
    var latitude: BigDecimal,
    var longitude: BigDecimal,
    var foreign: String,
)
