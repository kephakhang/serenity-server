package com.siksinhot.test.server.jpa.twin.entity

import com.siksinhot.test.server.jpa.twin.enum.PaymentMethod
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "payment_info")
data class PaymentInfo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,

    @Column(name = "tr_no")
    val trNo: String,               // 결제번호

    @Column(name = "tr_price")
    val trPrice: String,            // 결제 금액

    @Column(name = "payment_at")
    val paymentAt: LocalDateTime,   // 결제 시간

    @Enumerated(EnumType.STRING)
    @Column(name = "method")
    val method: PaymentMethod,      // 결제 방법

    @Column(name = "cell_ids")
    val cellIds: String? = null,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cell_id")
    val cell: Cell? = null,

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    val user: User,
) : BaseEntity()
