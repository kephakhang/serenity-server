package com.emoldino.serenity.server.jpa.test.dto


import com.fasterxml.jackson.annotation.JsonIgnore
import com.emoldino.serenity.server.jpa.everytalk.entity.Agent
import com.emoldino.serenity.server.jpa.test.entity.Goods
import com.emoldino.serenity.server.jpa.test.entity.Page
import io.swagger.annotations.ApiModelProperty
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.persistence.*

data class GoodsDto(

  var page: PageDto? = null,

  val seqNo: Long? = null,

  val pgSeqNo: Long? = null,

  val categorySeqNo: Long? = null,

  val name: String? = null,

  val hashtag: String? = null,

  val description: String? = null,

  val count: Long = -1L,

  val status: Int = 1,

  val goodsProp: String? = null,

  val price: Float? = null,

  val expireDatetime: Date? = null,

  val attachments: String? = null,

  val soldCount: Long = 0L,

  val lang: String = "ko",

  val type: Int = 0,

  val rewardLuckybol: Int = 0,

  val originPrice: Float? = null,

  val expireDay: Int? = null,

  val isHotdeal: Boolean = false,

  val isPlus: Boolean = false,

  val startTime: LocalTime? = null,

  val endTime: LocalTime? = null,

  val rewardPrLink: Int = 0,

  val newsDatetime: Date? = null,

  val rewardPrReviewLink: Int = 0,

  val regDatetime: LocalDateTime? = null,

  val modDatetime: LocalDateTime? = null
) {
  fun toGoods(): Goods {
    val dto = this
    val goods = Goods()
    return goods.apply {
      page = dto.page?.toPage();
      pgSeqNo = dto.pgSeqNo;
      goCategorySeqNo = dto.categorySeqNo;
      goName = dto.name;
      goHashtag = dto.hashtag;
      goDescription = dto.description;
      goCount = dto.count;
      goStatus = dto.status;
      goGoodsProp = dto.goodsProp;
      goPrice = dto.price;
      goExpireDatetime = dto.expireDatetime;
      goAttachments = dto.attachments;
      goSoldCount = dto.soldCount;
      goLang = dto.lang;
      goType = dto.type;
      goRewardLuckybol = dto.rewardLuckybol;
      goOriginPrice = dto.originPrice;
      goExpireDay = dto.expireDay;
      goIsHotdeal = dto.isHotdeal;
      goStartTime = dto.startTime;
      goEndTime = dto.endTime;
      goRewardPrLink = dto.rewardPrLink;
      goNewsDatetime = dto.newsDatetime;
      goRewardPrReviewLink = dto.rewardPrReviewLink;
    }
  }
}
