package io.warren.backend.fixtures

import io.warren.backend.entities.AccountRequest
import io.warren.backend.enums.AccountTypeEnum

class AccountRequestFixture(
    var username: String? = "random_username",
    var customerName: String? = "random_customername",
    var type: AccountTypeEnum? = AccountTypeEnum.CHECKING_ACCOUNT,
) {

    fun withoutUsername(): AccountRequestFixture {
        this.username = null
        return this
    }

    fun withoutCustomerName(): AccountRequestFixture {
        this.customerName = null
        return this
    }

    fun withoutType(): AccountRequestFixture {
        this.type = null
        return this
    }

    fun withUsername(username: String): AccountRequestFixture {
        this.username = username
        return this
    }

    fun withType(type: AccountTypeEnum): AccountRequestFixture {
        this.type = type
        return this
    }

    fun build(): AccountRequest {
        return AccountRequest(
            username = username,
            customerName = customerName,
            type = type,
        )
    }
}