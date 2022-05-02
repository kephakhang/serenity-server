package com.emoldino.serenity.server.service

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.SignupDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.entity.Member
import com.emoldino.serenity.server.jpa.own.repository.MemberRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class UserService(val db: MemberRepository) {

    @Throws(Exception::class)
    fun getUser(id: String): UserDto {
        val member = db.findByUuid(id) ?: throw SessionNotFoundException(null, "User Not Found")
        return member.toUserDto()
    }

    fun getList(pageno: Long, pagesize: Long): List<UserDto> {
        return db.findAll((pageno - 1) * pagesize, pagesize).map { it -> it.toUserDto() }.toList()
    }

    @Throws(Exception::class)
    fun update(userDto: UserDto): UserDto {
        var member = userDto.toMember()
        db.transaction {
            member = db.update(member)
        }
        return member.toUserDto()
    }


    @Throws(Exception::class)
    fun save(userDto: UserDto): Member {
        val member = userDto.toMember()
        return db.insert(member)
    }
}
