package com.siksinhot.test.server.service.twin

import com.siksinhot.test.repository.PlaceRepository
import com.siksinhot.test.server.jpa.twin.entity.Place


class PlaceService(
    private val placeRepository: PlaceRepository
) {

    //지역 대분류 리스트
    fun getPlace(id: Long): Place? {
        return placeRepository.findById(id)
    }

}

