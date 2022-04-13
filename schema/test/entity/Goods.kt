package com.emoldino.serenity.server.jpa.test.entity


import com.fasterxml.jackson.annotation.JsonIgnore
import com.emoldino.serenity.server.jpa.test.dto.GoodsDto
import io.swagger.annotations.ApiModelProperty
import java.time.LocalTime
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "b1_goods")
class Goods(

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pg_seq_no", columnDefinition = "bigint", insertable = false, updatable = false)
  var page: Page? = null,

  /**
   * 상품 상점 페이지 순번
   */
  @ApiModelProperty("상품 상점 페이지 순번")
  @Column(name = "pg_seq_no", nullable = false)
  var pgSeqNo: Long? = null,

  /**
   * 상품 카테고리 순번
   */
  @ApiModelProperty("상품 카테고리 순번")
  @Column(name = "go_category_seq_no", nullable = false)
  var goCategorySeqNo: Long? = null,

  /**
   * 상품명
   */
  @ApiModelProperty("상품명")
  @Column(name = "go_name", nullable = false)
  var goName: String? = null,

  /**
   * 해쉬태그
   */
  @ApiModelProperty("해쉬태그")
  @Column(name = "go_hashtag")
  var goHashtag: String? = null,

  /**
   * 상품 설명
   */
  @ApiModelProperty("상품 설명")
  @Column(name = "go_description", nullable = false)
  var goDescription: String? = null,

  /**
   * 상품 수량,  -1 : 수량 제한 없음
   */
  @ApiModelProperty("상품 수량,  -1 : 수량 제한 없음")
  @Column(name = "go_count", nullable = false)
  var goCount: Long = -1L,

  /**
   * 상품상태  1:판매중(sail), 0:완판(soldOut), -1:판매종료(expire), -2: 판매중지(stop)
   */
  @Column(name = "go_status", nullable = false)
  @ApiModelProperty("상품상태  1:판매중(sail), 0:완판(soldOut), -1:판매종료(expire), -2: 판매중지(stop)")
  var goStatus: Int = 1,

  /**
   * 상품 옵션
   */
  @ApiModelProperty("상품 옵션")
  @Column(name = "go_goods_prop")
  var goGoodsProp: String? = null,


  /**
   * 상품 가격
   */
  @ApiModelProperty("상품 가격")
  @Column(name = "go_price", nullable = false)
  var goPrice: Float? = null,

  @Column(name = "go_expire_datetime")
  var goExpireDatetime: Date? = null,

  /**
   * 상품 이미지 리스트
   */
  @ApiModelProperty("상품 이미지 리스트")
  @Column(name = "go_attachments")
  var goAttachments: String? = null,

  /**
   * 상품 누적 판매 수량
   */
  @ApiModelProperty("상품 누적 판매 수량")
  @Column(name = "go_sold_count", nullable = false)
  var goSoldCount: Long = 0L,

  /**
   * 언어
   */
  @ApiModelProperty("언어")
  @Column(name = "go_lang", nullable = false)
  var goLang: String = "ko",

  /**
   * 0 : 메뉴 오더 상품, 1 : 일반 구매 상품
   */
  @Column(name = "go_type", nullable = false)
  @ApiModelProperty("0 : 메뉴 오더 상품, 1 : 일반 구매 상품")
  var goType: Int = 0,

  /**
   * 상품에 걸린 리워드 럭키볼 수
   */
  @ApiModelProperty("상품에 걸린 리워드 럭키볼 수")
  @Column(name = "go_reward_luckybol", nullable = false)
  var goRewardLuckybol: Int = 0,

  /**
   * 상품 원 가격
   */
  @ApiModelProperty("상품 원 가격")
  @Column(name = "go_origin_price", nullable = false)
  var goOriginPrice: Float? = null,

  /**
   * 구 매 후 사용처리 유효 기간
   */
  @Column(name = "go_expire_day")
  @ApiModelProperty("구 매 후 사용처리 유효 기간")
  var goExpireDay: Int? = null,

  /**
   * 핫딜 상품 여부
   */
  @ApiModelProperty("핫딜 상품 여부")
  @Column(name = "go_is_hotdeal", columnDefinition = "bit", nullable = false)
  var goIsHotdeal: Boolean = false,

  /**
   * 플러스 상품 여부
   */
  @ApiModelProperty("플러스 상품 여부")
  @Column(name = "go_is_plus", columnDefinition = "bit", nullable = false)
  var goIsPlus: Boolean = false,

  /**
   * 상품 구매 시작 시간
   */
  @Column(name = "go_start_time")
  @ApiModelProperty("상품 구매 시작 시간")
  var goStartTime: LocalTime? = null,

  /**
   * 상품 구매 종료 시간
   */
  @Column(name = "go_end_time")
  @ApiModelProperty("상품 구매 종료 시간")
  var goEndTime: LocalTime? = null,

  /**
   * PRLink 를 통해서 구매 시 리워드 럭키볼 수
   */
  @Column(name = "go_reward_pr_link")
  @ApiModelProperty("PRLink 를 통해서 구매 시 리워드 럭키볼 수")
  var goRewardPrLink: Int = 0,

  /**
   * 최신 소식으로 등록 시각
   */
  @Column(name = "go_news_datetime")
  @ApiModelProperty("최신 소식으로 등록 시각")
  var goNewsDatetime: Date? = null,

  /**
   * 구매 리뷰 광고를 통해서 구매 시 리워드 럭키볼 수
   */
  @Column(name = "go_reward_pr_review_link")
  @ApiModelProperty("구매 리뷰 광고를 통해서 구매 시 리워드 럭키볼 수")
  var goRewardPrReviewLink: Int = 0
): BaseEntity() {
  fun toGoodsDto(): GoodsDto {
    return GoodsDto().copy(
      seqNo = seqNo,
      pgSeqNo = pgSeqNo,
      categorySeqNo = goCategorySeqNo,
      name = goName,
      hashtag = goHashtag,
      description = goDescription,
      count = goCount,
      status = goStatus,
      goodsProp = goGoodsProp,
      price = goPrice,
      expireDatetime = goExpireDatetime,
      attachments = goAttachments,
      soldCount = goSoldCount,
      lang = goLang,
      type = goType,
      rewardLuckybol = goRewardLuckybol,
      originPrice = goOriginPrice,
      expireDay = goExpireDay,
      isHotdeal = goIsHotdeal,
      startTime = goStartTime,
      endTime = goEndTime,
      rewardPrLink = goRewardPrLink,
      newsDatetime = goNewsDatetime,
      rewardPrReviewLink = goRewardPrReviewLink,
      regDatetime = regDatetime,
      modDatetime = modDatetime
    )
  }
}
