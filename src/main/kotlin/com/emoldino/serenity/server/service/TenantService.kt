package com.emoldino.serenity.server.service

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.entity.Tenant
import com.emoldino.serenity.server.jpa.own.repository.TenantRepository

class TenantService(val db: TenantRepository) {

    @Throws(Exception::class)
    fun getTenant(id: String): TenantDto {
        val tenant = db.findById(id) ?: throw SessionNotFoundException(null, "Tenant Not Found")
        return tenant.toTenantDto()
    }

    fun getList(): List<TenantDto> {
        return db.findAll().map{it -> it.toTenantDto()}.toList()
    }

    @Throws(Exception::class)
    fun update(tenantDto: TenantDto): TenantDto {
        var tenant = tenantDto.toTenant()
        db.transaction {
            tenant = db.update(tenant)
        }
        return tenant.toTenantDto()
    }


    @Throws(Exception::class)
    fun save(tenantDto: TenantDto): Tenant {
        val tenant = tenantDto.toTenant()
        return db.insert(tenant)
    }
}
