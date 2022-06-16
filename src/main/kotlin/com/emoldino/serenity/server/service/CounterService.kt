package com.emoldino.serenity.server.service

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.CounterDto
import com.emoldino.serenity.server.jpa.own.entity.Counter
import com.emoldino.serenity.server.jpa.own.repository.CounterRepository

class CounterService(val db: CounterRepository) {

    @Throws(Exception::class)
    fun getCounter(id: String): CounterDto {
        val counter = db.findById(id) ?: throw SessionNotFoundException(null, "Counter Not Found")
        return counter.toCounterDto()
    }

    fun getList(tenantId: String?, terminalId: String?): List<CounterDto> {
        return db.findAll(tenantId, terminalId).map{it -> it.toCounterDto()}.toList()
    }

    @Throws(Exception::class)
    fun update(counterDto: CounterDto): CounterDto {
        var counter = counterDto.toCounter()
        db.transaction {
            counter = db.update(counter)
        }
        return counter.toCounterDto()
    }


    @Throws(Exception::class)
    fun save(counterDto: CounterDto): Counter {
        val counter = counterDto.toCounter()
        return db.insert(counter)
    }
}
