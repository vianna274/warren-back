package io.warren.backend.entities

import io.warren.backend.enums.AccountTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime

@Document(collection = "accounts")
data class Account (
    @Id
    val id: ObjectId = ObjectId(),

    val username: String,
    val customerName: String,
    val availableBalance: BigDecimal = BigDecimal.ZERO,
    val type: AccountTypeEnum,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)