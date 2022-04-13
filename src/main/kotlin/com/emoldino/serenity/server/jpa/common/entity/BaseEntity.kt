package com.emoldino.serenity.server.jpa.common.entity

//import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.lang.reflect.Modifier
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
class BaseEntity : Serializable {

  /**
   * 고유 ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Id
  @Column(name = "id", columnDefinition = "char(36)")
  var id: String? = null


  /**
   * tenant ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Column(name = "te_id", columnDefinition = "char(36)", nullable = false)
  var teId: String? = null

  /**
   * 등록시각
   */
//  @ApiModelProperty("등록시각")
  @CreationTimestamp
  @Column(name = "reg_datetime", nullable = false, updatable = false)
  lateinit var regDatetime: LocalDateTime

  /**
   * 변경시각
   */
//  @ApiModelProperty("수정시각")
  @UpdateTimestamp
  @Column(name = "mod_datetime", nullable = false)
  lateinit var modDatetime: LocalDateTime

  override fun equals(other: Any?): Boolean {
    other ?: return false

    if (this === other) return true

    other as BaseEntity

    return (this.id === other.id)
  }

  override fun hashCode(): Int {
    return if (this.id != null) {
      this.id.hashCode()
    } else {
      0
    }
  }

  override fun toString() = "Entity of type ${this.javaClass.name} with id: ${id}:${teId}"

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
