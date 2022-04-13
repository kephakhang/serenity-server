package com.emoldino.serenity.server.jpa.test.dto


import com.emoldino.serenity.server.jpa.everytalk.entity.Member
import com.emoldino.serenity.server.jpa.test.entity.Page
import java.time.LocalDateTime
import java.time.LocalTime

data class PageDto(

  val seqNo: Long? = null,

  val mbSeqNo: Long? = null,

  val coopSeqNo: Long? = null,

  val coopStatus: String = "normal",

  val status: String = "normal",

  val pageName: String? = null,

  val code: String? = null,

  val phoneNumber: String? = null,

  val openBounds: String = "everybody",

  val zipCode: String? = null,

  val roadAddress: String? = null,

  val roadDetailAddress: String? = null,

  val parcelAddress: String? = null,

  val parcelDetailAddress: String? = null,

  val latitude: Double? = null,

  val longitude: Double? = null,

  val catchphrase: String? = null,

  val introduction: String? = null,

  val categoryText: String? = null,

  val todayViewCount: Long = 0L,

  val totalViewCount: Long = 0L,

  val blind: String = "Y",

  val talkReceiveBounds: String = "everybody",

  val talkDenyDay: String? = null,

  val talkDenyStartTime: LocalTime? = null,

  val talkDenyEndTime: LocalTime? = null,

  val customerCount: Int = 0,

  val plusCount: Int = 0,

  val mainMovieUrl: String? = null,

  val pageProp: String? = null,

  val pageType: String = "store",

  val pageLevel: Int = 1,

  val modifierSeqNo: Long? = null,

  val profileSeqNo: Long? = null,

  val bgSeqNo: Long? = null,

  val valuationCount: Long = 0L,

  val valuationPoint: Long = 0L,

  val offerResDatetime: LocalDateTime? = null,

  val virtualPage: String = "N",

  val searchKeyword: String? = null,

  val authCode: String? = null,

  val incorrectAuthCodeCount: Int = 0,

  val agentSeqNo: Long? = null,

  val recommendationCode: String? = null,

  val settlementUrl: String? = null,

  val mainGoodsSeqNo: Long? = null,

  /**
   * 상품판배 가능 여부
   */
  val isSeller: Boolean = false,

  /**
   * 링크 페이지 여부
   */
  val isLink: Boolean = false,

  /**
   * 홈 페이지 링크
   */
  val homepageLink: String? = null,

  /**
   * 해쉬태그
   */
  val hashtag: String? = null,

  /**
   * 테마 카테고리 순번
   */
  val themaSeqNo: Long? = null,

  /**
   * 휴일 휴무 유무
   */
  val isHolidayClosed: Boolean = false,

  /**
   * 배달 반경
   */
  val deliveryRadius: Double? = null,

  /**
   * 매자 결제 가능여부
   */
  val isShopOrderable: Boolean = false,

  /**
   * 포장주문
   */
  val isPackingOrderable: Boolean = false,

  /**
   * 배달주문
   */
  val isDeliveryOrderable: Boolean = false,

  /**
   * 배달비
   */
  val deliveryFee: Float? = null,

  /**
   * 배달가능 최소 주문금액
   */
  val deliveryMinPrice: Float? = null,

  /**
   * 주차가능여부
   */
  val isParkingAvailable: Boolean = false,

  /**
   * 발렛주차가능여부
   */
  val isValetParkingAvailable: Boolean = false,

  /**
   * 체인점 여부
   */
  val isChain: Boolean = false,

  /**
   * 배달대행 여부
   */
  val isDelivery: Boolean = false,

  /**
   * 체인점 지점인 경우만 본점 페이지 순번 값이 있고 그외에는 null
   */
  val parentSeqNo: Long? = null,

  val usePrnumber: Boolean = false,

  val regDatetime: LocalDateTime = LocalDateTime.now(),

  val modDatetime: LocalDateTime = LocalDateTime.now(),

  val goodsList: MutableList<GoodsDto> = mutableListOf()

) {

  fun toPage(): Page {
    val dto = this
    val page = Page()
    return page.apply {
      seqNo = dto.seqNo;
      mbSeqNo = dto.mbSeqNo;
      pgCoopSeqNo = dto.coopSeqNo;
      pgCoopStatus = dto.coopStatus;
      pgStatus = dto.status;
      pgPageName = dto.pageName;
      pgCode = dto.code;
      pgPhoneNumber = dto.phoneNumber;
      pgOpenBounds = dto.openBounds;
      pgZipCode = dto.zipCode;
      pgRoadAddress = dto.roadAddress;
      pgRoadDetailAddress = dto.roadDetailAddress;
      pgParcelAddress = dto.parcelAddress;
      pgParcelDetailAddress = dto.parcelDetailAddress;
      pgLatitude = dto.latitude;
      pgLongitude = dto.longitude;
      pgCatchphrase = dto.catchphrase;
      pgIntroduction = dto.introduction;
      pgCategoryText = dto.categoryText;
      pgTodayViewCount = dto.todayViewCount;
      pgTotalViewCount = dto.totalViewCount;
      pgBlind = dto.blind;
      pgTalkReceiveBounds = dto.talkReceiveBounds;
      pgTalkDenyDay = dto.talkDenyDay;
      pgTalkDenyStartTime = dto.talkDenyStartTime;
      pgTalkDenyEndTime = dto.talkDenyEndTime;
      pgCustomerCount = dto.customerCount;
      pgPlusCount = dto.plusCount;
      pgMainMovieUrl = dto.mainMovieUrl;
      pgPageProp = dto.pageProp;
      pgPageType = dto.pageType;
      pgPageLevel = dto.pageLevel;
      pgModifierSeqNo = dto.modifierSeqNo;
      pgProfileSeqNo = dto.profileSeqNo;
      pgBgSeqNo = dto.bgSeqNo;
      pgValuationCount = dto.valuationCount;
      pgValuationPoint = dto.valuationPoint;
      pgOfferResDatetime = dto.offerResDatetime;
      pgVirtualPage = dto.virtualPage;
      pgSearchKeyword = dto.searchKeyword;
      pgAuthCode = dto.authCode;
      pgIncorrectAuthCodeCount = dto.incorrectAuthCodeCount;
      pgAgentSeqNo = dto.agentSeqNo;
      pgRecommendationCode = dto.recommendationCode;
      pgSettlementUrl = dto.settlementUrl;
      pgMainGoodsSeqNo = dto.mainGoodsSeqNo;
      pgIsSeller = dto.isSeller;
      pgIsLink = dto.isLink;
      pgHomepageLink = dto.homepageLink;
      pgHashtag = dto.hashtag;
      pgThemaSeqNo = dto.themaSeqNo;
      pgIsHolidayClosed = dto.isHolidayClosed;
      pgDeliveryRadius = dto.deliveryRadius;
      pgIsShopOrderable = dto.isShopOrderable;
      pgIsPackingOrderable = dto.isPackingOrderable;
      pgIsDeliveryOrderable = dto.isDeliveryOrderable;
      pgDeliveryFee = dto.deliveryFee;
      pgDeliveryMinPrice = dto.deliveryMinPrice;
      pgIsParkingAvailable = dto.isParkingAvailable;
      pgIsValetParkingAvailable = dto.isValetParkingAvailable;
      pgIsChain = dto.isChain;
      pgIsDelivery = dto.isDelivery;
      pgParentSeqNo = dto.parentSeqNo;
      pgUsePrnumber = dto.usePrnumber;
      regDatetime = dto.regDatetime;
      modDatetime = dto.modDatetime;
      dto.goodsList?.let {
        goodsList = dto.goodsList.map { it.toGoods() }.toMutableList()
      }
    }
  }

}
