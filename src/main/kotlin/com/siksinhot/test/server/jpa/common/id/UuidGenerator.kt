package com.siksinhot.test.server.jpa.common.id

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable
import java.util.*

class UuidGenerator : IdentifierGenerator {
  override fun generate(
    session: SharedSessionContractImplementor,
    entity: Any
  ): Serializable {
    return UUID.randomUUID().toString()
  }
}
