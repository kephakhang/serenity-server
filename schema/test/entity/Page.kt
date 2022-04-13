package com.emoldino.serenity.server.jpa.test.entity


import com.emoldino.serenity.server.jpa.test.dto.PageDto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*


@Entity
@Table(name = "b1_page")
class Page(

  @Column(name = "mb_seq_no", nullable = false)
  var mbSeqNo: Long? = null,

  @Column(name = "pg_coop_seq_no")
  var pgCoopSeqNo: Long? = null,

  @Column(name = "pg_coop_status", nullable = false)
  var pgCoopStatus: String = "normal",

  @Column(name = "pg_status", nullable = false)
  var pgStatus: String = "normal",

  @Column(name = "pg_page_name", nullable = false)
  var pgPageName: String? = null,

  @Column(name = "pg_code")
  var pgCode: String? = null,

  @Column(name = "pg_phone_number")
  var pgPhoneNumber: String? = null,

  @Column(name = "pg_open_bounds", nullable = false)
  var pgOpenBounds: String = "everybody",

  @Column(name = "pg_zip_code")
  var pgZipCode: String? = null,

  @Column(name = "pg_road_address")
  var pgRoadAddress: String? = null,

  @Column(name = "pg_road_detail_address")
  var pgRoadDetailAddress: String? = null,

  @Column(name = "pg_parcel_address")
  var pgParcelAddress: String? = null,

  @Column(name = "pg_parcel_detail_address")
  var pgParcelDetailAddress: String? = null,

  @Column(name = "pg_latitude")
  var pgLatitude: Double? = null,

  @Column(name = "pg_longitude")
  var pgLongitude: Double? = null,

  @Column(name = "pg_catchphrase")
  var pgCatchphrase: String? = null,

  @Column(name = "pg_introduction")
  var pgIntroduction: String? = null,

  @Column(name = "pg_category_text")
  var pgCategoryText: String? = null,

  @Column(name = "pg_today_view_count", nullable = false)
  var pgTodayViewCount: Long = 0L,

  @Column(name = "pg_total_view_count", nullable = false)
  var pgTotalViewCount: Long = 0L,

  @Column(name = "pg_blind", columnDefinition = "ENUM('Y','N')", nullable = false)
  var pgBlind: String = "Y",

  @Column(name = "pg_talk_receive_bounds")
  var pgTalkReceiveBounds: String = "everybody",

  @Column(name = "pg_talk_deny_day")
  var pgTalkDenyDay: String? = null,

  @Column(name = "pg_talk_deny_start_time")
  var pgTalkDenyStartTime: LocalTime? = null,

  @Column(name = "pg_talk_deny_end_time")
  var pgTalkDenyEndTime: LocalTime? = null,

  @Column(name = "pg_customer_count", nullable = false)
  var pgCustomerCount: Int = 0,

  @Column(name = "pg_plus_count", nullable = false)
  var pgPlusCount: Int = 0,

  @Column(name = "pg_main_movie_url")
  var pgMainMovieUrl: String? = null,

  @Column(name = "pg_page_prop", columnDefinition = "TEXT")
  var pgPageProp: String? = null,

  @Column(name = "pg_page_type", nullable = false)
  var pgPageType: String = "store",

  @Column(name = "pg_page_level", nullable = false)
  var pgPageLevel: Int = 1,

  @Column(name = "pg_modifier_seq_no")
  var pgModifierSeqNo: Long? = null,

  @Column(name = "pg_profile_seq_no")
  var pgProfileSeqNo: Long? = null,

  @Column(name = "pg_bg_seq_no")
  var pgBgSeqNo: Long? = null,

  @Column(name = "pg_valuation_count", nullable = false)
  var pgValuationCount: Long = 0L,

  @Column(name = "pg_valuation_point", nullable = false)
  var pgValuationPoint: Long = 0L,

  @Column(name = "pg_offer_res_datetime")
  var pgOfferResDatetime: LocalDateTime? = null,

  @Column(name = "pg_virtual_page", columnDefinition = "ENUM('Y','N')", nullable = false)
  var pgVirtualPage: String = "N",

  @Column(name = "pg_search_keyword")
  var pgSearchKeyword: String? = null,

  @Column(name = "pg_auth_code")
  var pgAuthCode: String? = null,

  @Column(name = "pg_incorrect_auth_code_count", nullable = false)
  var pgIncorrectAuthCodeCount: Int = 0,

  @Column(name = "pg_agent_seq_no")
  var pgAgentSeqNo: Long? = null,

  @Column(name = "pg_recommendation_code")
  var pgRecommendationCode: String? = null,

  @Column(name = "pg_settlement_url")
  var pgSettlementUrl: String? = null,

  @Column(name = "pg_main_goods_seq_no")
  var pgMainGoodsSeqNo: Long? = null,

  /**
   * 상품판배 가능 여부
   */
  @Column(name = "pg_is_seller", columnDefinition = "BIT")
  @ApiModelProperty("상품판배 가능 여부")
  var pgIsSeller: Boolean = false,

  /**
   * 링크 페이지 여부
   */
  @ApiModelProperty("링크 페이지 여부")
  @Column(name = "pg_is_link", columnDefinition = "BIT", nullable = false)
  var pgIsLink: Boolean = false,

  /**
   * 홈 페이지 링크
   */
  @ApiModelProperty("홈 페이지 링크")
  @Column(name = "pg_homepage_link")
  var pgHomepageLink: String? = null,

  /**
   * 해쉬태그
   */
  @ApiModelProperty("해쉬태그")
  @Column(name = "pg_hashtag")
  var pgHashtag: String? = null,

  /**
   * 테마 카테고리 순번
   */
  @ApiModelProperty("테마 카테고리 순번")
  @Column(name = "pg_thema_seq_no")
  var pgThemaSeqNo: Long? = null,

  /**
   * 휴일 휴무 유무
   */
  @ApiModelProperty("휴일 휴무 유무")
  @Column(name = "pg_is_holiday_closed", columnDefinition = "BIT", nullable = false)
  var pgIsHolidayClosed: Boolean = false,

  /**
   * 배달 반경
   */
  @ApiModelProperty("배달 반경")
  @Column(name = "pg_delivery_radius")
  var pgDeliveryRadius: Double? = null,

  /**
   * 매자 결제 가능여부
   */
  @ApiModelProperty("매자 결제 가능여부")
  @Column(name = "pg_is_shop_orderable", columnDefinition = "BIT")
  var pgIsShopOrderable: Boolean = false,

  /**
   * 포장주문
   */
  @ApiModelProperty("포장주문")
  @Column(name = "pg_is_packing_orderable", columnDefinition = "BIT", nullable = false)
  var pgIsPackingOrderable: Boolean = false,

  /**
   * 배달주문
   */
  @ApiModelProperty("배달주문")
  @Column(name = "pg_is_delivery_orderable", columnDefinition = "BIT", nullable = false)
  var pgIsDeliveryOrderable: Boolean = false,

  /**
   * 배달비
   */
  @ApiModelProperty("배달비")
  @Column(name = "pg_delivery_fee")
  var pgDeliveryFee: Float? = null,

  /**
   * 배달가능 최소 주문금액
   */
  @ApiModelProperty("배달가능 최소 주문금액")
  @Column(name = "pg_delivery_min_price")
  var pgDeliveryMinPrice: Float? = null,

  /**
   * 주차가능여부
   */
  @ApiModelProperty("주차가능여부")
  @Column(name = "pg_is_parking_available", columnDefinition = "BIT", nullable = false)
  var pgIsParkingAvailable: Boolean = false,

  /**
   * 발렛주차가능여부
   */
  @ApiModelProperty("발렛주차가능여부")
  @Column(name = "pg_is_valet_parking_available", columnDefinition = "BIT", nullable = false)
  var pgIsValetParkingAvailable: Boolean = false,

  /**
   * 체인점 여부
   */
  @ApiModelProperty("체인점 여부")
  @Column(name = "pg_is_chain", columnDefinition = "BIT", nullable = false)
  var pgIsChain: Boolean = false,

  /**
   * 배달대행 여부
   */
  @ApiModelProperty("배달대행 여부")
  @Column(name = "pg_is_delivery", columnDefinition = "BIT", nullable = false)
  var pgIsDelivery: Boolean = false,

  /**
   * 체인점 지점인 경우만 본점 페이지 순번 값이 있고 그외에는 null
   */
  @Column(name = "pg_parent_seq_no")
  @ApiModelProperty("체인점 지점인 경우만 본점 페이지 순번 값이 있고 그외에는 null")
  var pgParentSeqNo: Long? = null,

  /**
   * prnumber 사용여부
   */
  @Column(name = "pg_use_prnumber", columnDefinition = "BIT")
  @ApiModelProperty("prnumber 사용여부")
  var pgUsePrnumber: Boolean = false,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "page", cascade = [CascadeType.ALL])
  var goodsList: MutableList<Goods> = mutableListOf()

) : BaseEntity() {
  fun addGoods(goods: Goods) {
    goods.page = this
    goodsList.add(goods)
  }

  fun toPageDto(): PageDto {
    return PageDto().copy(
      seqNo = seqNo,
      mbSeqNo = mbSeqNo,
      coopSeqNo = pgCoopSeqNo,
      coopStatus = pgCoopStatus,
      status = pgStatus,
      pageName = pgPageName,
      code = pgCode,
      phoneNumber = pgPhoneNumber,
      openBounds = pgOpenBounds,
      zipCode = pgZipCode,
      roadAddress = pgRoadAddress,
      roadDetailAddress = pgRoadDetailAddress,
      parcelAddress = pgParcelAddress,
      parcelDetailAddress = pgParcelDetailAddress,
      latitude = pgLatitude,
      longitude = pgLongitude,
      catchphrase = pgCatchphrase,
      introduction = pgIntroduction,
      categoryText = pgCategoryText,
      todayViewCount = pgTodayViewCount,
      totalViewCount = pgTotalViewCount,
      blind = pgBlind,
      talkReceiveBounds = pgTalkReceiveBounds,
      talkDenyDay = pgTalkDenyDay,
      talkDenyStartTime = pgTalkDenyStartTime,
      talkDenyEndTime = pgTalkDenyEndTime,
      customerCount = pgCustomerCount,
      plusCount = pgPlusCount,
      mainMovieUrl = pgMainMovieUrl,
      pageProp = pgPageProp,
      pageType = pgPageType,
      pageLevel = pgPageLevel,
      modifierSeqNo = pgModifierSeqNo,
      profileSeqNo = pgProfileSeqNo,
      bgSeqNo = pgBgSeqNo,
      valuationCount = pgValuationCount,
      valuationPoint = pgValuationPoint,
      offerResDatetime = pgOfferResDatetime,
      virtualPage = pgVirtualPage,
      searchKeyword = pgSearchKeyword,
      authCode = pgAuthCode,
      incorrectAuthCodeCount = pgIncorrectAuthCodeCount,
      agentSeqNo = pgAgentSeqNo,
      recommendationCode = pgRecommendationCode,
      settlementUrl = pgSettlementUrl,
      mainGoodsSeqNo = pgMainGoodsSeqNo,
      isSeller = pgIsSeller,
      isLink = pgIsLink,
      homepageLink = pgHomepageLink,
      hashtag = pgHashtag,
      themaSeqNo = pgThemaSeqNo,
      isHolidayClosed = pgIsHolidayClosed,
      deliveryRadius = pgDeliveryRadius,
      isShopOrderable = pgIsShopOrderable,
      isPackingOrderable = pgIsPackingOrderable,
      isDeliveryOrderable = pgIsDeliveryOrderable,
      deliveryFee = pgDeliveryFee,
      deliveryMinPrice = pgDeliveryMinPrice,
      isParkingAvailable = pgIsParkingAvailable,
      isValetParkingAvailable = pgIsValetParkingAvailable,
      isChain = pgIsChain,
      isDelivery = pgIsDelivery,
      parentSeqNo = pgParentSeqNo,
      usePrnumber = pgUsePrnumber,
      regDatetime = regDatetime,
      modDatetime = modDatetime,
      goodsList = goodsList?.map{ it.toGoodsDto() }.toMutableList()
    )
  }
}
