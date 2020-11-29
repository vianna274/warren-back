package io.warren.backend.converters

import io.warren.backend.dtos.AccountDto
import io.warren.backend.dtos.AccountOption
import io.warren.backend.entities.Account
import org.springframework.stereotype.Component

@Component
class AccountConverter {
    fun convert(account: Account): AccountDto {
        return AccountDto(
            id = account.id.toString(),
            username = account.username,
            customerName = account.customerName,
            availableBalance = account.availableBalance,
            type = account.type,
            createdDate = account.createdDate,
            modifiedDate = account.modifiedDate,
        )
    }

    fun convertToSummary(account: Account): AccountOption {
        return AccountOption(
            value = account.id.toString(),
            label = account.username,
        )
    }
}