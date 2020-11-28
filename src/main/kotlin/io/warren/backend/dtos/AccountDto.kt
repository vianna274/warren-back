package io.warren.backend.dtos

import io.warren.backend.enums.AccountTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
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