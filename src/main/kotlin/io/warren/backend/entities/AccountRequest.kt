package io.warren.backend.entities

import io.warren.backend.enums.AccountTypeEnum
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AccountRequest (
    @get:NotNull(message = "username cant be empty")
    @get:Size(min = 5, max = 100)
    val username: String?,

    @get:NotNull(message = "customerName cant be empty")
    @get:Size(min = 5, max = 100)
    val customerName: String?,

    @get:NotNull(message = "type cant be empty")
    val type: AccountTypeEnum?
)