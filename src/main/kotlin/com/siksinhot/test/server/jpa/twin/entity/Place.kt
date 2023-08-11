package com.siksinhot.test.server.jpa.twin.entity


import com.siksinhot.test.server.jpa.twin.entity.BaseEntity
import com.siksinhot.twinkorea.converter.BooleanToYNConverter
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "place")
data class Place(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "pid")
    val pid: Long,   // '식신 장소 분류번호'

    @Column(name = "p_name")
    val pName: String, // '장소 명'

    @Column(name = "main_area_id")
    val mainAreaId: Int, //'식신 장소 대분류 번호'

    @Column(name = "main_area_title")
    val mainAreaTitle:String, //'식신 장소 대분류 명'

    @Column(name = "sub_area_id")
    val subAreaId:Int,    // '식신 장소 소분류 번호'

    @Column(name = "sub_area_title")
    val subAreaTitle: String, //'식신 장소 소분류 명'

    @Column(name = "siksin_mcate_no")
    val siksinMcateNo: Int,  // '식신 업종 소분류 번호'

    @Column(name = "sub_category_name")
    val subCategoryName: String , // '식신 업종 소분류 명'

    @Column(name = "latitude", columnDefinition = "decimal(10, 6)")
    val latitude: Double, //'위도

    @Column(name = "longitude", columnDefinition = "decimal(10, 6)")
    val longitude: Double,  // '경도'

    @Column(name = "number_addr")
    val numberAddr: String?,  //'지번주소'

    @Column(name = "road_addr")
    val roadAddr: String?,  //'도로명주소'

    @Column(name = "addr_detail")
    val addrDetail: String?, // '주소상세'

    @Column(name = "cmt")
    val cmt: String?, //'한줄소개',

    @Column(name = "intro")
    val intro: String?, // '매장소개'

    @Column(name = "homepage")
    val homepage: String?,  // '홈페이지 (,로 구분)'

    @Column(name = "phone_number")
    val phoneNumber: String?,  // '전화번호'

    @Column(name = "close_type", columnDefinition = "CHAR")
    val closeType: String,  // '매장상태 (N:정상, A:내부수리중, B:임시휴업)',


    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_takeout", columnDefinition = "CHAR")
    val isTakeout: Boolean,  // '테이크아웃여부',

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_tbl_os", columnDefinition = "CHAR")
    val isTblOs: Boolean, // '야외좌석여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_resrv", columnDefinition = "CHAR")
    val isResry: Boolean, // '예약가능여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_package", columnDefinition = "CHAR")
    val isPackage: Boolean, // '포장가능여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_parking", columnDefinition = "CHAR")
    val isParking: Boolean, // '주차가능여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_vallet", columnDefinition = "CHAR")
    val isVallet: Boolean, // '발렛가능여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_wifi", columnDefinition = "CHAR")
    val isWifi: Boolean, // '와이파이여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_wheelchair", columnDefinition = "CHAR")
    val isWheelchair: Boolean, // '휠체어좌석여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_pet", columnDefinition = "CHAR")
    val isPet: Boolean, // '애완동물 출입가능여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_baby", columnDefinition = "CHAR")
    val isBaby: Boolean, // '유아시트 가능여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_smoking", columnDefinition = "CHAR")
    val isSmoking: Boolean, // '흡연가능 여부'

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_ko", columnDefinition = "CHAR")
    val isKo: Boolean,

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "is_corkage", columnDefinition = "CHAR")
    val isCorkage: Boolean,

    @Column(name = "currency")
    val currency: String,

    @Column(name = "lang")
    val lang: String = "kr",

    @Column(name = "station")
    val station: String?,

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime?,

    @Column(name = "like_cnt")
    val likeCnt: Long, //'좋아요 수'

    @Column(name = "bookmark_cnt")
    val bookmarkCnt: Long, //'북마크(담기) 수'

    @Column(name = "review_cnt")
    var reviewCnt: Long, //'리뷰 수'

    @Column(name = "view_cnt")
    val viewCnt: Long, //'조회 수'

    @Column(name = "score")
    val score: Double, //'평점'

    @Column(name = "cell_id")
    var cellId: Long,

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "hotplace", columnDefinition = "CHAR")
    val hotplace: Boolean, // '핫플레이스 여부'


    @Column(name = "stct")
    val stct: Int?,

    @Column(name = "tot_rank")
    val totRank: Int
) : BaseEntity() {

}