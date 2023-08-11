package com.siksinhot.test.server.jpa.twin.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "cell_info")
data class CellInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "cell_id")
    val cellId: Long = 0,

    @Column(name = "center_city")
    val centerCity: String? = null,

    @Column(name = "center_x")
    val centerX: Double? = null,

    @Column(name = "center_y")
    val centerY: Double? = null,

    @Column(name = "is_in_range")
    val isInRange: Boolean = true,

    @Column(name = "left_bottom")
    val leftBottom: String? = null,

    @Column(name = "left_top")
    val leftTop: String? = null,

    @Column(name = "right_bottom")
    val rightBottom: String? = null,

    @Column(name = "right_top")
    val rightTop: String? = null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    val owner: User? = null,

    @Column(name = "area_id")
    val areaId: Long? = null,

    @Column(name = "reserved")
    val reserved: Boolean = false,

    @Column(name = "place_cnt")
    val placeCnt: Int = 0

    ) : BaseEntity()
