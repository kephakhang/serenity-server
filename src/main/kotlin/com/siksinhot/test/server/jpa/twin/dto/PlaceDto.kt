package com.siksinhot.test.server.jpa.twin.dto


import java.time.LocalDateTime

class PlaceDto(

    val pid: Int,   // '식신 장소 분류번호'
    val pName: String, // '장소 명'
    val mainAreaId: Int, //'식신 장소 대분류 번호'
    val mainAreaTitle:String, //'식신 장소 대분류 명'
    val subAreaId:Int,    // '식신 장소 소분류 번호'
    val subAreaTitle: String, //'식신 장소 소분류 명'
    val siksinMcateNo: Int,  // '식신 업종 소분류 번호'
    val subCategoryName: String , // '식신 업종 소분류 명'
    val latitude: Double, //'위도
    val longitude: Double,  // '경도'
    val numberAddr: String?,  //'지번주소'
    val roadAddr: String?,  //'도로명주소'
    val addrDetail: String?, // '주소상세'
    val cmt: String?, //'한줄소개',
    val intro: String?, // '매장소개'
    val homepage: String?,  // '홈페이지 (,로 구분)'
    val phoneNumber: String?,  // '전화번호'
    val closeType: String?,  // '매장상태 (N:정상, A:내부수리중, B:임시휴업)',
    val isTakeout: Boolean?,  // '테이크아웃여부',
    val isTblOs: Boolean?, // '야외좌석여부'
    val isResry: Boolean?, // '야외좌석여부'
    val isPackage: Boolean?, // '포장가능여부'
    val isParking: Boolean?, // '주차가능여부'
    val isVallet: Boolean?, // '발렛가능여부'
    val isWifi: Boolean?, // '와이파이여부'
    val isWheelchair: Boolean?, // '휠체어좌석여부'
    val isPet: Boolean?, // '애완동물 출입가능여부'
    val isBaby: Boolean?, // '유아시트 가능여부'
    val isSmoking: Boolean?, // '흡연가능 여부'
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime?,
    val likeCnt: Double, //'좋아요 수'
    val bookmarkCnt: Double, //'북마크(담기) 수'
    val reviewCnt: Double, //'리뷰 수'
    val viewCnt: Double, //'조회 수'
    val score: Double, //'평점'
    val cellId: Long,
    val hotplace: Boolean, // '핫플레이스 여부'
    val stct: String?,
    val totRank: Int
)