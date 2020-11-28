package io.warren.backend.entities

import io.warren.backend.enums.TransactionTypeEnum
import org.bson.types.ObjectId
import java.math.BigDecimal
import javax.validation.constraints.NotNull

class TransactionRequest (
    @get:NotNull(message = "amount cant be empty")
    val amount: BigDecimal?,
    val sourceAccountId: ObjectId?,
    @get:NotNull
    val destinationAccountId: ObjectId?,
    @get:NotNull
    val type: TransactionTypeEnum?
)