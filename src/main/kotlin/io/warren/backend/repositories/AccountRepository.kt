package io.warren.backend.repositories

import io.warren.backend.entities.Account
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface AccountRepository : ReactiveMongoRepository<Account, String> {
    fun findAccountByUsername(username: String): Flux<Account>
}