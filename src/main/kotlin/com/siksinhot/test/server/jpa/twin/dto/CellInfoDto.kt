package com.siksinhot.test.cellinfo.dto

data class CellInfoDto(
    var cellId: Long,
    var centerCity: String?,
    var centerX: Double?,
    var centerY: Double?,
    var isInRange: Boolean?,
    var leftBottom: String?,
    var leftTop: String?,
    var rightBottom: String?,
    var rightTop: String?,
    var ownerId: Long?,
    var ownerCi: String?,
    var areaId: Long?,
    var reserved: Boolean,
    var placeCnt: Int,
)
