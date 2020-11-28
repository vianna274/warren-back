package io.warren.backend.repositories

import io.warren.backend.entities.Transaction
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TransactionsRepository : ReactiveMongoRepository<Transaction, String> {
    fun findAllBySourceAccountIdOrDestinationAccountId(sourceAccountId: ObjectId, destinationAccountId: ObjectId): Flux<Transaction>
}