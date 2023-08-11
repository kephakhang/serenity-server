package com.siksinhot.test.server.jpa.twin.entity

import com.siksinhot.test.common.LocalDateTimeSerializer
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity {

    @CreationTimestamp
    @Convert(converter  = LocalDateTimeSerializer::class)
    @Column(name = "created_at", columnDefinition = "DATETIME", nullable = false, updatable = false)
    lateinit var createdAt: LocalDateTime

    @CreationTimestamp
    @Convert(converter  = LocalDateTimeSerializer::class)
    @Column(name = "updated_at", columnDefinition = "DATETIME", nullable = false, updatable = false)
    lateinit var updatedAt: LocalDateTime
}