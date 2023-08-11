package com.siksinhot.test.server.jpa.twin.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "main_area")
data class MainArea(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "area_id", columnDefinition = "int(11)")
    val areaId: Int,

    @Column(name = "area_title", columnDefinition = "varchar(50)")
    val areaTitle: String,

    @Column(columnDefinition = "decimal(10,6)")
    val latitude: BigDecimal,

    @Column(columnDefinition = "decimal(10,6)")
    val longitude: BigDecimal,

    @Column(name = "is_foreign", columnDefinition = "char(1)")
    val isForeign: String,

) : BaseEntity()
