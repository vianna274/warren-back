package io.warren.backend.fixtures

import io.warren.backend.entities.Account
import io.warren.backend.entities.Transaction
import io.warren.backend.enums.AccountTypeEnum
import io.warren.backend.enums.TransactionTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionFixture(
    @Id
    var id: ObjectId = ObjectId(),

    var amount: BigDecimal = BigDecimal.ZERO,
    var sourceAccountId: ObjectId? = null,
    var destinationAccountId: ObjectId? = null,
    var type: TransactionTypeEnum = TransactionTypeEnum.DEPOSIT,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var modifiedDate: LocalDateTime = LocalDateTime.now()
) {

    fun withId(id: ObjectId): TransactionFixture {
        this.id = id
        return this
    }

    fun withSourceAccountId(sourceAccountId: ObjectId): TransactionFixture {
        this.sourceAccountId = sourceAccountId
        return this
    }

    fun withDestinationAccountId(destinationAccountId: ObjectId): TransactionFixture {
        this.destinationAccountId = destinationAccountId
        return this
    }


    fun build(): Transaction {
        return Transaction(
            id = id,
            amount = amount,
            sourceAccountId = sourceAccountId,
            destinationAccountId = destinationAccountId,
            type = type,
            createdDate = createdDate,
            modifiedDate = modifiedDate,
        )
    }
}