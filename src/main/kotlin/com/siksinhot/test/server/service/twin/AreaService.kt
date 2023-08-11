package com.siksinhot.test.server.service.twin

import com.siksinhot.test.area.dto.AreaMainDto
import com.siksinhot.test.area.dto.AreaSubDto
import com.siksinhot.test.paymentinfo.repository.MainAreaRepository
import com.siksinhot.test.paymentinfo.repository.SubAreaRepository
import com.siksinhot.test.server.jpa.twin.entity.MainArea
import com.siksinhot.test.server.jpa.twin.entity.SubArea


class AreaService(
    private val areaMainGroupRepository: MainAreaRepository,
    private val areaSubGroupRepository: SubAreaRepository,
) {

    //지역 대분류 리스트
    fun getAreaMainList(): List<AreaMainDto> {
        val areaMainList = areaMainGroupRepository.findAll()

        return areaMainList.map { areaMainGroup: MainArea ->
//            println("areaMainGroup.isForeign ::: " + areaMainGroup.isForeign)
            AreaMainDto(
                id = areaMainGroup.id,
                areaId = areaMainGroup.areaId,
                areaTitle = areaMainGroup.areaTitle,
                latitude = areaMainGroup.latitude,
                longitude = areaMainGroup.longitude,
//                isForeign = areaMainGroup.isForeign,
                foreign = areaMainGroup.isForeign,
            )
        }
    }

    //지역 소분류 리스트
    fun getAreaSubList(mainAreaId: Long): List<AreaSubDto> {
        val areaSubList = areaSubGroupRepository.findAllByMainAreaId(mainAreaId = mainAreaId)

        return areaSubList.map { areaSubGroup: SubArea ->
            AreaSubDto(
                id = areaSubGroup.id,
                areaId = areaSubGroup.areaId.toInt(),
                areaTitle = areaSubGroup.areaTitle,
                latitude = areaSubGroup.latitude,
                longitude = areaSubGroup.longitude,
                mainAreaId = areaSubGroup.mainArea?.id ?: 0
            )
        }
    }

}

