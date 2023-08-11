package com.siksinhot.test.server.jpa.twin.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users_detail")
data class UserDetail(

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    var user: User? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long? = 0,

    @Column(name = "comm_id")
    val commId: String, //이동통신사

    @Column(name = "phone_number")
    val phoneNumber: String, //인증된 휴대폰번호

    @Column(name = "name")
    val name: String,   //사용자 실명

    @Column(name = "social_no")
    val socialNo: String,   //생년월일

    @Column(name = "gender")
    val gender: String, //성별

    @Column(name = "foreigner")
    val foreigner: String,  //외국인여부(Y:외국인/N:내국인)

    @Column(name = "ci")
    val ci: String, //ci

    @Column(name = "di")
    val di: String? = null, //di

): BaseEntity()