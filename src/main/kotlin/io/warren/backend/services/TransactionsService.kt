package io.warren.backend.services

import io.warren.backend.converters.TransactionConverter
import io.warren.backend.dtos.TransactionDto
import io.warren.backend.entities.Transaction
import io.warren.backend.entities.TransactionRequest
import io.warren.backend.enums.TransactionTypeEnum
import io.warren.backend.repositories.TransactionsRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class TransactionsService {
    @Autowired
    private val transactionsRepository: TransactionsRepository? = null

    @Autowired
    private val accountsService: AccountsService? = null

    @Autowired
    private val transactionConverter: TransactionConverter? = null

    @Autowired
    private val template: ReactiveMongoTemplate? = null

    fun createTransaction(request: TransactionRequest): Mono<TransactionDto> {
        val transaction = Transaction(
            amount = request.amount!!,
            type = request.type!!,
            sourceAccountId = request.sourceAccountId,
            destinationAccountId = request.destinationAccountId
        )

        if (transaction.destinationAccountId == transaction.sourceAccountId) {
            throw ResponseStatusException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Não é possível realizar a operação para contas iguais."
            )
        }

        return when (transaction.type) {
            TransactionTypeEnum.DEPOSIT -> deposit(transaction)
            TransactionTypeEnum.TRANSFER -> transfer(transaction)
            TransactionTypeEnum.PAYMENT -> withdrawalOrPayment(transaction)
            TransactionTypeEnum.WITHDRAWAL -> withdrawalOrPayment(transaction)
        }
    }

    fun findTransactionHistory(accountId: String): Flux<TransactionDto> {
        return transactionsRepository!!.findAllBySourceAccountIdOrDestinationAccountId(ObjectId(accountId), ObjectId(accountId))
            .map { transactionConverter!!.convert(it) }
    }

    private fun transfer(transaction: Transaction): Mono<TransactionDto> {
        val sourceAccountMono =
            accountsService!!.getSubtractAccountBalance(transaction.sourceAccountId.toString(), transaction.amount)
        val destinationAccountMono = accountsService!!.getAddAccountAvailableBalance(
            transaction.destinationAccountId!!.toHexString(),
            transaction.amount
        )

        return Mono.zip(sourceAccountMono, destinationAccountMono)
            .flatMap { tuple ->
                val sourceAccount = tuple.t1
                val destinationAccount = tuple.t2

                template!!.inTransaction().execute {
                    it.save(sourceAccount)
                        .then(it.save(destinationAccount))
                        .then(it.insert(transaction))
                }
                    .map { transactionConverter!!.convert(it) }
                    .next()
            }
    }

    private fun withdrawalOrPayment(transaction: Transaction): Mono<TransactionDto> {
        return accountsService!!.getSubtractAccountBalance(
            transaction.sourceAccountId.toString(),
            transaction.amount
        )
            .flatMap { account ->
                template!!.inTransaction().execute {
                    it.save(account)
                        .then(it.insert(transaction))
                }
                    .map { transactionConverter!!.convert(it) }
                    .next()
            }
    }

    private fun deposit(transaction: Transaction): Mono<TransactionDto> {
        return accountsService!!.getAddAccountAvailableBalance(
            transaction.destinationAccountId.toString(),
            transaction.amount
        )
            .flatMap { account ->
                template!!.inTransaction().execute {
                    it.save(account)
                        .then(it.insert(transaction))
                }
                    .map { transactionConverter!!.convert(it) }
                    .next()
            }
    }
}