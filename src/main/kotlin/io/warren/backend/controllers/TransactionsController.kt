package io.warren.backend.controllers

import io.warren.backend.dtos.TransactionDto
import io.warren.backend.entities.TransactionRequest
import io.warren.backend.services.TransactionsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class TransactionsController {
    @Autowired
    private val transactionsService: TransactionsService? = null

    @PostMapping
    fun createTransaction(
        @Valid @RequestBody request: TransactionRequest,
        @RequestHeader(value = "transactionId") transactionId: String
    ): Mono<TransactionDto> {
        return transactionsService!!.createTransaction(request)
    }

    @GetMapping()
    fun findTransactionHistoryByAccountId(
        @RequestParam(name = "accountId") accountId: String,
        @RequestHeader(value = "transactionId") transactionId: String
    ): Flux<TransactionDto> {
        return transactionsService!!.findTransactionHistory(accountId)
    }
}