package io.warren.backend.controllers

import io.warren.backend.dtos.AccountDto
import io.warren.backend.entities.AccountRequest
import io.warren.backend.services.AccountsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
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
}