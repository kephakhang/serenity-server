package com.siksinhot.test.server.jpa.twin.entity

import java.sql.Types.TINYINT
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "client_white_list")
data class ClientWhiteList(
    @Id
    @Column(name = "ip")
    val ip: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "active", columnDefinition = "TINYINT(1)")
    val active: Boolean,

) : BaseEntity()
