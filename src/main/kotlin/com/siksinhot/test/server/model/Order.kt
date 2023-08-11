package com.siksinhot.test.server.model

import java.math.BigDecimal

data class Order(
  val price: BigDecimal,
  val amount: BigDecimal
)
