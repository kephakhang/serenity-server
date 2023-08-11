package com.siksinhot.test.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.*
import com.siksinhot.test.server.jpa.twin.entity.User
import java.util.*
import kotlin.reflect.KClass

class ReviewRepository : Querydsl4RepositorySupport<User>(Env.em, Review::class as KClass<Any>) {
    val path: PathBuilder<Review> = PathBuilder<Review>(Review::class.java, "review")
    val review: QReview = QReview.review
}
