package io.warren.backend.converters

import io.warren.backend.dtos.TransactionDto
import io.warren.backend.entities.Transaction
import org.springframework.stereotype.Component

@Component
class TransactionConverter {
    fun convert(transaction: Transaction): TransactionDto {
        return TransactionDto(
            id = transaction.id.toString(),
            type = transaction.type,
            sourceAccountId = transaction.sourceAccountId.toString(),
            destinationAccountId = transaction.destinationAccountId.toString(),
            amount = transaction.amount,
            createdDate = transaction.createdDate,
            modifiedDate = transaction.modifiedDate,
        )
    }
}