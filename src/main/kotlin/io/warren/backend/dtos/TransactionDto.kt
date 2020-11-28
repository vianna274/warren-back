package io.warren.backend.dtos

import io.warren.backend.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionDto (
    val id: String,
    val destinationAccountId: String,
    val sourceAccountId: String,
    val amount: BigDecimal,
    val type: TransactionTypeEnum,
    val createdDate: LocalDateTime,
    val modifiedDate: LocalDateTime
)