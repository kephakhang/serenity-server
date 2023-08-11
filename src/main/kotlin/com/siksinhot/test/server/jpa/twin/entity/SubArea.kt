package com.siksinhot.test.server.jpa.twin.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "sub_area")
data class SubArea(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "area_id")
    val areaId: Int,

    @Column(name = "area_title", columnDefinition = "varchar(50)")
    val areaTitle: String,

    @Column(columnDefinition = "decimal(10,6)")
    val latitude: BigDecimal,

    @Column(columnDefinition = "decimal(10,6)")
    val longitude: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_area_group_id", columnDefinition = "INT")
    val mainArea: MainArea? = null,
) : BaseEntity()
