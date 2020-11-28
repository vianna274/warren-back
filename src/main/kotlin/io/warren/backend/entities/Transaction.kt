package io.warren.backend.entities

import io.warren.backend.enums.TransactionTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.validation.constraints.NotNull

@Document(collection = "transactions")
data class Transaction (
    @Id
    val id: ObjectId = ObjectId(),

    val amount: BigDecimal,
    val sourceAccountId: ObjectId?,
    val destinationAccountId: ObjectId,
    val type: TransactionTypeEnum,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)