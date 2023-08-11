package com.siksinhot.test.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.User
import com.siksinhot.test.server.jpa.twin.entity.*
import kotlin.reflect.KClass

class UserRepository : Querydsl4RepositorySupport<User>(Env.em, User::class as KClass<Any>) {
    val path: PathBuilder<User> = PathBuilder<User>(User::class.java, "user")
    val user: QUser = QUser.user

    fun findById(id: Long): User? {
        return selectFrom(user).where(user.id.eq(id)).fetchOne()
    }
    fun findByIdAndDeactivateIsFalse(id: Long?): User? {
        return selectFrom(user).where(user.id.eq(id).and(user.deactivate.eq(false))).fetchOne()
    }

    fun findByReferralCodeAndDeactivateIsFalse(referralCode: String): User? {
        return selectFrom(user).where(user.referralCode.eq(referralCode).and(user.deactivate.eq(false))).fetchOne()
    }

    fun findBySnsIdAndDeactivateIsFalse(snsId: String): User? {
        return selectFrom(user).where(user.snsId.eq(snsId).and(user.deactivate.eq(false))).fetchOne()
    }

    fun findByEmailAndDeactivateIsFalse(email: String): User? {
        return selectFrom(user).where(user.email.eq(email).and(user.deactivate.eq(false))).fetchOne()
    }

    fun findByEmailAndDeactivateIsFalseAndAdminEquals(email: String, admin: Boolean): User? {
        return selectFrom(user).where(user.email.eq(email).and(user.deactivate.eq(false)).and(user.admin.eq(admin))).fetchOne()
    }

    fun findByNicknameAndDeactivateIsFalse(nickname: String): User? {
        return selectFrom(user).where(user.nickname.eq(nickname).and(user.deactivate.eq(false))).fetchOne()
    }

    fun findTop10ByIdIsNotAndDeactivateIsFalseOrderByInvitingCountDesc(id: Long = 0): MutableList<User> {
        return selectFrom(user).where(user.id.ne(id).and(user.deactivate.eq(false))).orderBy(user.invitingCount.desc()).limit(10L).fetch()
    }

    fun findAllById(id: Long): User? {
        return selectFrom(user).where(user.id.eq(id)).fetchOne()
    }
}
