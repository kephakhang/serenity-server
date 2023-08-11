package com.siksinhot.test.server.service.twin

import com.siksinhot.test.cell.repository.CellRepository
import com.siksinhot.test.cellinfo.dto.CellInfoDto
import com.siksinhot.test.cellinfo.repository.CellITradeHistoryRepository
import com.siksinhot.test.cellinfo.repository.CellInfoRepository
import com.siksinhot.test.exception.ErrorCode
import com.siksinhot.test.exception.TwinException
import com.siksinhot.test.repository.UserRepository
import com.siksinhot.test.server.jpa.common.repository.Page
import com.siksinhot.test.server.jpa.common.repository.PageRequest
import com.siksinhot.test.server.jpa.common.repository.Pageable
import com.siksinhot.test.server.jpa.twin.entity.CellInfo
import com.siksinhot.test.server.jpa.twin.entity.CellTradeHistory
import com.siksinhot.test.server.jpa.twin.entity.User
import mu.KotlinLogging


private val logger = KotlinLogging.logger {}
class CellInfoService(
    private val cellRepository: CellRepository,
    private val userDetailRepository: UserRepository,
    private val cellInfoRepository: CellInfoRepository,
    private val cellTradeHistoryRepository: CellITradeHistoryRepository
    ) {

    fun getAll(pageRequest: PageRequest): Page {
        val page = cellInfoRepository.findAllByOrderByCellId(pageRequest)

        page.content = page.content.map {it: Any ->

            val cellInfo: CellInfo = it as CellInfo

            CellInfoDto(
                cellId = cellInfo.cellId,
                centerCity = cellInfo.centerCity,
                centerX = cellInfo.centerX,
                centerY = cellInfo.centerY,
                isInRange = cellInfo.isInRange,
                leftBottom = cellInfo.leftBottom,
                leftTop = cellInfo.leftTop,
                rightBottom = cellInfo.rightBottom,
                rightTop = cellInfo.rightTop,
                ownerId = cellInfo.owner?.id,
                ownerCi = cellInfo.owner?.detail?.ci,
                areaId = cellInfo.areaId,
                reserved = cellInfo.reserved,
                placeCnt = cellInfo.placeCnt
            )
        }

        return page
    }

    fun getUserCell(userId: Long, pageRequest: Pageable): Page {
        val page = cellInfoRepository.findAllByOwnerIdOrderByCellId(ownerId = userId, pageRequest = pageRequest)
            ?: throw TwinException(ErrorCode.E10015)

        page.content = page.content.map { it: Any ->
            val cellInfo: CellInfo = it as CellInfo
            CellInfoDto(
                cellId = cellInfo.cellId,
                centerCity = cellInfo.centerCity,
                centerX = cellInfo.centerX,
                centerY = cellInfo.centerY,
                isInRange = cellInfo.isInRange,
                leftBottom = cellInfo.leftBottom,
                leftTop = cellInfo.leftTop,
                rightBottom = cellInfo.rightBottom,
                rightTop = cellInfo.rightTop,
                ownerId = cellInfo.owner?.id,
                ownerCi = cellInfo.owner?.detail?.ci,
                areaId = cellInfo.areaId,
                reserved = cellInfo.reserved,
                placeCnt = cellInfo.placeCnt
            )
        }

        return page
    }

}

