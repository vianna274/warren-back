package io.warren.backend.fixtures

import io.warren.backend.entities.Account
import io.warren.backend.enums.AccountTypeEnum
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime

class AccountFixture(
    var id: ObjectId = ObjectId(),

    var username: String = "random_username",
    var customerName: String = "random_customername",
    var availableBalance: BigDecimal = BigDecimal.ZERO,
    var type: AccountTypeEnum = AccountTypeEnum.CHECKING_ACCOUNT,
    var createdDate: LocalDateTime = LocalDateTime.now(),
    var modifiedDate: LocalDateTime = LocalDateTime.now()
) {

    fun withUsername(username: String): AccountFixture {
        this.username = username
        return this
    }

    fun withCustomerName(customerName: String): AccountFixture {
        this.customerName = customerName
        return this
    }

    fun withId(id: ObjectId): AccountFixture {
        this.id = id
        return this
    }

    fun withType(type: AccountTypeEnum): AccountFixture {
        this.type = type
        return this
    }

    fun withAvailableBalance(availableBalance: BigDecimal): AccountFixture {
        this.availableBalance = availableBalance
        return this
    }

    fun build(): Account {
        return Account(
            id = id,
            username = username,
            customerName = customerName,
            availableBalance = availableBalance,
            type = type,
            createdDate = createdDate,
            modifiedDate = modifiedDate,
        )
    }
}