package com.emoldino.serenity.server.jpa.test.entity


import io.swagger.annotations.ApiModelProperty
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.lang.reflect.Modifier
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity : Serializable {

  /**
   * 고유 ID(seqNo)
   */
  @Id
  @ApiModelProperty("상품 순번")
  @Column(name = "seq_no", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var seqNo: Long? = null

  /**
   * 등록시각
   */
  @ApiModelProperty("등록시각")
  @CreationTimestamp
  @Column(name = "reg_datetime", nullable = false, updatable = false)
  lateinit var regDatetime: LocalDateTime

  /**
   * 변경시각
   */
  @ApiModelProperty("수정시각")
  @UpdateTimestamp
  @Column(name = "mod_datetime", nullable = false)
  lateinit var modDatetime: LocalDateTime

  override fun equals(other: Any?): Boolean {
    other ?: return false

    if (this === other) return true

    other as BaseEntity

    return (this.seqNo === other.seqNo)
  }

  override fun hashCode(): Int {
    return if (this.seqNo != null) {
      this.seqNo.hashCode()
    } else {
      0
    }
  }

  override fun toString() = "Entity of type ${this.javaClass.name} with id: ${seqNo}"

  fun reflectionToString(obj: Any): String {
    val s = LinkedList<String>()
    var clazz: Class<in Any>? = obj.javaClass
    while (clazz != null) {
      for (prop in clazz.declaredFields.filterNot { Modifier.isStatic(it.modifiers) }) {
        prop.isAccessible = true
        s += "${prop.name}=" + prop.get(obj)?.toString()?.trim()
      }
      clazz = clazz.superclass
    }
    return "${obj.javaClass.simpleName}=[${s.joinToString(", ")}]"
  }
}

enum class UserStatus {
  registered, leaved, unconfirmed, dormant, failed, saved, deleted
}

enum class RefundStatus {
  denied, canceled, filled, requested
}
