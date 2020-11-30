package io.warren.backend.services

import io.warren.backend.converters.AccountConverter
import io.warren.backend.converters.TransactionConverter
import io.warren.backend.dtos.AccountDto
import io.warren.backend.dtos.AccountOption
import io.warren.backend.entities.Account
import io.warren.backend.entities.AccountRequest
import io.warren.backend.repositories.AccountRepository
import io.warren.backend.repositories.TransactionsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class AccountsService {
    @Autowired
    private val accountsRepository: AccountRepository? = null

    @Autowired
    private val transactionsRepository: TransactionsRepository? = null

    @Autowired
    private val accountConverter: AccountConverter? = null

    @Autowired
    private val transactionConverter: TransactionConverter? = null

    fun findAllAccounts(): Flux<AccountOption> {
        return accountsRepository!!.findAll()
            .map { accountConverter!!.convertToSummary(it) }
    }

    fun createAccount(request: AccountRequest): Mono<AccountDto> {
        val account = Account(
            customerName = request.customerName!!,
            type = request.type!!,
            username = request.username!!
        )

        return accountsRepository!!.findAllByUsername(request.username.orEmpty())
            .collectList()
            .flatMap {
                if (it.size > 0) {
                    throw ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Já existe uma conta com esse username"
                    )
                }
                accountsRepository!!.save(account)
            }
            .map { accountConverter!!.convert(it) }
    }

    fun getAccountByid(id: String): Mono<AccountDto> {
        return accountsRepository!!.findById(id)
            .map { accountConverter!!.convert(it) }
    }

    fun getAccountByUsername(username: String): Mono<AccountDto> {
        return accountsRepository!!.findAllByUsername(username)
            .map { accountConverter!!.convert(it) }
            .collectList()
            .flatMap { accounts ->
                if (accounts.size == 0) {
                    throw ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe uma conta com esse username")
                }

                if (accounts.size > 1) {
                    throw ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Há mais de uma conta para esse username"
                    )
                }

                Mono.just(accounts[0])
            }
    }

    fun getAddAccountAvailableBalance(accountId: String, amount: BigDecimal): Mono<Account> {
        return accountsRepository!!.findById(accountId)
            .map {
                Account(
                    id = it.id,
                    availableBalance = it.availableBalance + amount,
                    createdDate = it.createdDate,
                    modifiedDate = LocalDateTime.now(),
                    username = it.username,
                    customerName = it.customerName,
                    type = it.type,
                )
            }
    }

    fun getSubtractAccountBalance(accountId: String, amount: BigDecimal): Mono<Account> {
        val account = accountsRepository!!.findById(accountId)
            .filter { it.availableBalance - amount >= BigDecimal.ZERO }
            .switchIfEmpty {
                throw ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Não há saldo suficiente para realizar a operação."
                )
            }
            .map {
                if (it.availableBalance - amount < BigDecimal.ZERO) {
                    throw ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Não há saldo suficiente para realizar a operação."
                    )
                }

                Account(
                    id = it.id,
                    availableBalance = it.availableBalance - amount,
                    createdDate = it.createdDate,
                    modifiedDate = LocalDateTime.now(),
                    username = it.username,
                    customerName = it.customerName,
                    type = it.type,
                )
            }

        return account
    }
}