package io.warren.backend.dtos

import io.warren.backend.enums.AccountTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime

class AccountDto (
    val id: String,
    val username: String,
    val customerName: String,
    val availableBalance: BigDecimal,
    val type: AccountTypeEnum,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime
)