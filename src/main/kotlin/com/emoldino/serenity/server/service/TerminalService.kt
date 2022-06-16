package com.emoldino.serenity.server.service

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.TerminalDto
import com.emoldino.serenity.server.jpa.own.entity.Terminal
import com.emoldino.serenity.server.jpa.own.repository.CounterRepository
import com.emoldino.serenity.server.jpa.own.repository.TerminalRepository

class TerminalService(val db: TerminalRepository, val counterDb: CounterRepository) {

    @Throws(Exception::class)
    fun getTerminal(id: String): TerminalDto {
        val terminal = db.findById(id) ?: throw SessionNotFoundException(null, "Terminal Not Found")
        return terminal.toTerminalDto()
    }

    fun getList(tenantId: String?): List<TerminalDto> {
        return db.findAll(tenantId).map{it -> it.toTerminalDto()}.toList()
    }

    @Throws(Exception::class)
    fun update(terminalDto: TerminalDto): TerminalDto {
        var terminal = terminalDto.toTerminal()
        db.transaction {
            terminal = db.update(terminal)
        }
        return terminal.toTerminalDto()
    }


    @Throws(Exception::class)
    fun save(terminalDto: TerminalDto): Terminal {
        val terminal = terminalDto.toTerminal()
        return db.insert(terminal)
    }
}
