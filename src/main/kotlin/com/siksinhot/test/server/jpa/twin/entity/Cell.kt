package com.siksinhot.test.server.jpa.twin.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name="cells")
data class Cell(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "left_top")
    val leftTop: String? = null,

    @Column(name = "right_top")
    val rightTop: String? = null,

    @Column(name = "left_bottom")
    val leftBottom: String? = null,

    @Column(name = "right_bottom")
    val rightBottom: String? = null,

    @Column(name = "center_x")
    val centerX: Double? = null,

    @Column(name = "center_y")
    val centerY: Double? = null,

    @Column(name = "is_in_range")
    val isInRange: Boolean = true,

    @Column(name = "center_city")
    val centerCity: String? = null,

    @Column(name = "area_id")
    val areaId: Long? = null,

    @Column(name = "on_payment")
    val onPayment: Boolean? = null,

    @Column(name = "on_payment_by")
    val onPaymentBy: Long? = null,

    @Column(name = "reserved")
    val reserved: Boolean = false,

    @Column(name = "is_purchase_coupon", columnDefinition = "bit")
    val isPurchaseCoupon: Boolean,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "land_id")
    val land: Land? = null,

    ) : BaseEntity()

@Entity
@Table(name="cells")
data class CellId(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,
)