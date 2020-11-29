package io.warren.backend.controllers

import io.warren.backend.dtos.AccountDto
import io.warren.backend.dtos.AccountOption
import io.warren.backend.entities.AccountRequest
import io.warren.backend.services.AccountsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class AccountsController {
    @Autowired
    private val accountsService: AccountsService? = null

    @GetMapping("/{id}")
    fun get(
        @PathVariable("id") id: String,
        @RequestHeader(value = "transactionId") transactionId: String
    ): Mono<AccountDto> {
        return accountsService!!.getAccountByid(id)
    }

    @GetMapping
    fun findByUsername(
        @RequestParam(name = "username") username: String,
        @RequestHeader(value = "transactionId") transactionId: String
    ): Mono<AccountDto> {
        return accountsService!!.getAccountByUsername(username)
    }

    @PostMapping
    fun createAccount(
        @Valid @RequestBody request: AccountRequest,
        @RequestHeader(value = "transactionId") transactionId: String
    ): Mono<AccountDto> {
        return accountsService!!.createAccount(request)
    }

    @GetMapping("/options")
    fun findAllAccountsOptions(
        @RequestHeader(value = "transactionId") transactionId: String
    ): Flux<AccountOption> {
        return accountsService!!.findAllAccounts()
    }
}