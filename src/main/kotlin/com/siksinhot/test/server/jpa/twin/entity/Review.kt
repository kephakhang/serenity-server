package com.siksinhot.test.server.jpa.twin.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.siksinhot.test.server.jpa.twin.enum.ReviewApprovalType
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "review")
data class Review(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "pid")
    val pid: Long,

    @Column(name = "code")
    val code: String,

    @Column(name = "user_id")
    val userId: Long,

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    val user: User?,

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
//    @JoinColumn(name = "cell_owner_id")
//    val cellOwner: User,

    @Column(name = "cell_owner_id")
    val cellOwnerId: Long,

    @Column(name = "score")
    val score: Double,

    @Column(name = "content")
    val content: String,

    @Column(name = "img_cnt")
    val imgCnt: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "is_approval")
    val isApproval: ReviewApprovalType = ReviewApprovalType.NONE,

    @Column(name = "closed_reason")
    val closedReason: String?,

    @Column(name = "is_deleted", columnDefinition = "TiNYINT(1)")
    val deleted: Boolean,

    @Column(name = "is_report", columnDefinition = "TiNYINT(1)")
    val isReport: Boolean,

    @Column(name = "recom_cnt")
    val recomCnt: Long,

    @Column(name = "deleted_user_id")
    val deletedUserId: Long? = null,

    @Column(name = "deleted_reason")
    val deletedReason: String?,

    @Column(name = "deleted_at")
    val deletedAt: LocalDateTime?,

    @Column(name = "approval_at")
    val approvalAt: LocalDateTime?,

    @Column(name = "is_cell_owner", columnDefinition = "TiNYINT(1)")
    val isCellOwner: Boolean,

    @Column(name = "is_siksin", columnDefinition = "TiNYINT(1)")
    val isSiksin: Boolean = false,

    @Column(name = "siksin_nickname")
    val siksinNickname: String? = null

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review")
//    @OrderBy(value = "updated_at")
//    val images: MutableList<StoreImage> = ArrayList(),
)
